package org.aoc.graphics;

import javax.imageio.*;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class AnimationMaker {

    private final ImageWriter imageWriter;
    private final ImageWriteParam imageWriteParam;
    private final IIOMetadata metadata;

    public AnimationMaker(ImageOutputStream out,
                          int imageType,
                          int delayMS,
                          boolean loop) throws IOException {

        imageWriter = ImageIO.getImageWritersByFormatName("gif").next();
        imageWriteParam = imageWriter.getDefaultWriteParam();

        ImageTypeSpecifier imageTypeSpecifier = ImageTypeSpecifier.createFromBufferedImageType(imageType);
        metadata = imageWriter.getDefaultImageMetadata(imageTypeSpecifier, imageWriteParam);

        String metaFormat = metadata.getNativeMetadataFormatName();
        IIOMetadataNode root = (IIOMetadataNode) metadata.getAsTree(metaFormat);

        // Delay time (in 1/100th seconds)
        IIOMetadataNode graphicsControlExtensionNode = getNode(
            root, "GraphicControlExtension");
        graphicsControlExtensionNode.setAttribute("delayTime", Integer.toString(delayMS / 10));
        graphicsControlExtensionNode.setAttribute("disposalMethod", "none");

        // Looping
        if (loop) {
            IIOMetadataNode appExtensions = getNode(root, "ApplicationExtensions");
            IIOMetadataNode appNode = new IIOMetadataNode("ApplicationExtension");
            appNode.setAttribute("applicationID", "NETSCAPE");
            appNode.setAttribute("authenticationCode", "2.0");
            appNode.setUserObject(new byte[]{1, 0, 0}); // loop forever
            appExtensions.appendChild(appNode);
        }

        metadata.setFromTree(metaFormat, root);

        imageWriter.setOutput(out);
        imageWriter.prepareWriteSequence(null);
    }

    public void writeToSequence(BufferedImage img) throws IOException {
        imageWriter.writeToSequence(new IIOImage(img, null, metadata), imageWriteParam);
    }

    public void close() throws IOException {
        imageWriter.endWriteSequence();
    }

    private static IIOMetadataNode getNode(IIOMetadataNode rootNode, String nodeName) {
        for (int i = 0; i < rootNode.getLength(); i++) {
            if (rootNode.item(i).getNodeName().equals(nodeName))
                return (IIOMetadataNode) rootNode.item(i);
        }
        IIOMetadataNode node = new IIOMetadataNode(nodeName);
        rootNode.appendChild(node);
        return node;
    }
}

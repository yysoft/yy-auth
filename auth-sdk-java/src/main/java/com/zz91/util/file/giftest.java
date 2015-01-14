package com.zz91.util.file;

/*
 * giftest - a class to test GIFEncoder
 *
 * by Adam Doppelt
 * http://www.cs.brown.edu/people/amd/
 */
import java.awt.AWTException;
import java.awt.Frame;
import java.awt.Image;
import java.awt.MediaTracker;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URL;

// This app will load the image URL given as the first argument, and
// save it as a GIF to the file given as the second argument. Beware
// of not having enough memory!
public class giftest  {
    public static void main(String args[]) throws Exception {
//	if (args.length != 2) {
//	    System.out.println("giftest [url to load] [output file]");
//	    return;
//	}

	// need a component in order to use MediaTracker
	Frame f = new Frame("GIFTest");

	// load an image
	Image image = f.getToolkit().getImage("/usr/data/resources/products/2011/12/22/13245178458704723.gif");
	
	// wait for the image to entirely load
	MediaTracker tracker = new MediaTracker(f);
	tracker.addImage(image, 0);
	try {
	    tracker.waitForID(0);
	} catch (InterruptedException e) { ; }
	if (tracker.statusID(0, true) != MediaTracker.COMPLETE)
	    throw new AWTException("Could not load: " + args[0] + " " +
				   tracker.statusID(0, true));
	
	// encode the image as a GIF
	GIFEncoder encode = new GIFEncoder(image);
	OutputStream output = new BufferedOutputStream(
	    new FileOutputStream("/usr/data/resources/products/2011/12/22/13245178458704723.mini.gif"));
	encode.Write(output);
	
	System.exit(0);
    }
}

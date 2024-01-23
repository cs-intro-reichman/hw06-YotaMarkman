// This class uses the Color class, which is part of a package called awt,
// which is part of Java's standard class library.
import java.awt.Color;

/** A library of image processing functions. */
public class Runigram 
{

	public static void main(String[] args)
	 {
	    
		//// Hide / change / add to the testing code below, as needed.
		
		// Tests the reading and printing of an image:	
		Color[][] tinypic = read("tinypic.ppm");
		print(tinypic);

		// Creates an image which will be the result of various 
		// image processing operations:
	//	Color[][] imageOut;

		// Tests the horizontal flipping of an image:
	//	imageOut = flippedVertically(tinypic);
	//	System.out.println();
	//	print(imageOut);
		
	//	imageOut = grayScaled(tinypic);
	//	System.out.println();
	//	print(imageOut);

	//	int height = 5;
	//	int width = 3;
	//	imageOut = scaled(tinypic, width, height);
	//	System.out.println();
	//	print(imageOut);
		//// Write here whatever code you need in order to test your work.
		//// You can reuse / overide the contents of the imageOut array.
	}

	/** Returns a 2D array of Color values, representing the image data
	 * stored in the given PPM file. */
	public static Color[][] read(String fileName) 
	{
		In in = new In(fileName);
		// Reads the file header, ignoring the first and the third lines.
		in.readString();
		int numCols = in.readInt();
		int numRows = in.readInt();
		in.readInt();
		// Creates the image array
		Color[][] image = new Color[numRows][numCols];
		// Reads the RGB values from the file, into the image array. 
		// For each pixel (i,j), reads 3 values from the file,
		// creates from the 3 colors a new Color object, and 
		// makes pixel (i,j) refer to that object.
		//// Replace the following statement with your code.
		for ( int i = 0; i < image.length; i++)
		{
			for ( int j = 0; j < image[i].length; j++)
			{
				image[i][j] = new Color(in.readInt(), in.readInt(), in.readInt());
			}
		}
		return image;
	}

    // Prints the RGB values of a given color.
	private static void print(Color c) 
	{
	    System.out.print("(");
		System.out.printf("%3s,", c.getRed());   // Prints the red component
		System.out.printf("%3s,", c.getGreen()); // Prints the green component
        System.out.printf("%3s",  c.getBlue());  // Prints the blue component
        System.out.print(")  ");
	}

	// Prints the pixels of the given image.
	// Each pixel is printed as a triplet of (r,g,b) values.
	// This function is used for debugging purposes.
	// For example, to check that some image processing function works correctly,
	// we can apply the function and then use this function to print the resulting image.
	private static void print(Color[][] image) 
	{
		for ( int i = 0; i < image.length; i++)
		{
			for ( int j = 0; j < image[i].length; j++)
			{
				Color pixel = image[i][j];
				print(pixel);
			}
			System.out.println();	
		}
	}
	
	/**
	 * Returns an image which is the horizontally flipped version of the given image. 
	 */
	public static Color[][] flippedHorizontally(Color[][] image)
	 {
		int rows = image.length;
		int cols = image[0].length;
		Color[][] imageFlipped = new Color[rows][cols];
		for (int i = 0; i < rows; i++)
		{
			for (int j = 0; j < cols; j++)
			{
				imageFlipped[i][j] = image[i][cols - j - 1];
			}
		}
		return imageFlipped;
	}	
	/**
	 * Returns an image which is the vertically flipped version of the given image. 
	 */
	public static Color[][] flippedVertically(Color[][] image)
	{
		int rows = image.length;
		int cols = image[0].length;
		Color[][] imageVertical = new Color[rows][cols];
		for ( int i = 0; i < rows; i++)
		{
			for ( int j = 0; j < cols; j++)
			{
				imageVertical[i][j] = image[rows - i - 1][j];
			}
		}
		return imageVertical;
	}
	
	// Computes the luminance of the RGB values of the given pixel, using the formula 
	// lum = 0.299 * r + 0.587 * g + 0.114 * b, and returns a Color object consisting
	// the three values r = lum, g = lum, b = lum.
	public static Color luminance(Color pixel) 
	{
		int r = pixel.getRed();
		int g = pixel.getGreen();
		int b = pixel.getBlue();
		int lum = (int) ((0.299 * r) + (0.587 * g) + (0.114 * b));
		Color lumC = new Color(lum, lum, lum);
		return lumC;
	}	
	/**
	 * Returns an image which is the grayscaled version of the given image.
	 */
	public static Color[][] grayScaled(Color[][] image) 
	{
		int rows = image.length;
		int cols = image[0].length;
		Color[][] scaled = new Color[rows][cols];
		for ( int i = 0; i < rows; i++)
		{
			for ( int j = 0; j < cols; j++)
			{
				Color pixel = image[i][j];
				Color newPixel = luminance(pixel);
				scaled[i][j] = newPixel;
			}
		}
		return scaled;
	}	
	
	/**
	 * Returns an image which is the scaled version of the given image. 
	 * The image is scaled (resized) to have the given width and height.
	 */
	public static Color[][] scaled(Color[][] image, int width, int height) 
	{
		Color[][] sized = new Color[height][width];
		int rows = image.length;
		int cols = image[0].length;
		for ( int i = 0; i < height; i++ )
		{
			for ( int j = 0; j < width; j++)
			{
				int newi = (int)(i * (rows / (double)height));
				int newj = (int)(j * (cols / (double)width));
				newi = Math.min( newi, rows - 1);
				newj = Math.min( newj, cols - 1 );
				sized[i][j] = image[newi][newj];
			}
		}
		return sized;
	}
	
	/**
	 * Computes and returns a blended color which is a linear combination of the two given
	 * colors. Each r, g, b, value v in the returned color is calculated using the formula 
	 * v = alpha * v1 + (1 - alpha) * v2, where v1 and v2 are the corresponding r, g, b
	 * values in the two input color.
	 */
	public static Color blend(Color c1, Color c2, double alpha) 
	{
		int r1 = c1.getRed();
		int g1 = c1.getGreen();
		int b1 = c1.getBlue();
		int r2 = c2.getRed();
		int g2 = c2.getGreen();
		int b2 = c2.getBlue();
		double alpha2 = 1 - alpha;
		int r = (int) (alpha * r1 + alpha2 * r2); // blended red value
		int g = (int) (alpha * g1 + alpha2 * g2); // blended green value
		int b = (int) (alpha * b1 + alpha2 * b2); // blended blue value
		r = Math.max(0, Math.min(255, r)); // clamp r to the range [0, 255]
		g = Math.max(0, Math.min(255, g)); // clamp g to the range [0, 255]
		b = Math.max(0, Math.min(255, b)); // clamp b to the range [0, 255]
		Color blended = new Color(r, g, b); // create the blended color
		return blended;
	}
	/**
	 * Cosntructs and returns an image which is the blending of the two given images.
	 * The blended image is the linear combination of (alpha) part of the first image
	 * and (1 - alpha) part the second image.
	 * The two images must have the same dimensions.
	 */
	public static Color[][] blend(Color[][] image1, Color[][] image2, double alpha) 
	{
		int rows = image1.length;
		int cols = image1[0].length;
		Color[][] blended = new Color[rows][cols];
		for ( int i = 0; i < rows; i++)
		{
			for ( int j = 0; j < cols; j++)
			{
				Color im1 = image1[i][j];
				Color im2 = image2[i][j];
				Color blend = blend(im1, im2, alpha);
				blended[i][j] = blend;
			}
		}
		return blended;
	}

	/**
	 * Morphs the source image into the target image, gradually, in n steps.
	 * Animates the morphing process by displaying the morphed image in each step.
	 * Before starting the process, scales the target image to the dimensions
	 * of the source image.
	 */
	public static void morph(Color[][] source, Color[][] target, int n)
	{
		int rows1 = source.length;
		int cols1 = source[0].length;
		int rows2 = target.length;
		int cols2 = target[0].length;
		if ( rows1 == rows2 && cols1 == cols2)
		{
			for ( int i = 0; i < n; i++)
			{
				double alpha = (n - i) / n;
				display(blend(source, target, alpha));
				StdDraw.pause(500);
			}
		}
		else
		{
			target = scaled(target, cols1, rows1);
			for ( int i = 0; i < n; i++)
			{
				double alpha = ( n - i) / n;
				display(blend(source, target, alpha));
				StdDraw.pause(500);
			}
		}
	}
	
	/** Creates a canvas for the given image. */
	public static void setCanvas(Color[][] image) 
	{
		StdDraw.setTitle("Runigram 2023");
		int height = image.length;
		int width = image[0].length;
		StdDraw.setCanvasSize(height, width);
		StdDraw.setXscale(0, width);
		StdDraw.setYscale(0, height);
        // Enables drawing graphics in memory and showing it on the screen only when
		// the StdDraw.show function is called.
		StdDraw.enableDoubleBuffering();
	}

	/** Displays the given image on the current canvas. */
	public static void display(Color[][] image) 
	{
		int height = image.length;
		int width = image[0].length;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				// Sets the pen color to the pixel color
				StdDraw.setPenColor( image[i][j].getRed(),
					                 image[i][j].getGreen(),
					                 image[i][j].getBlue() );
				// Draws the pixel as a filled square of size 1
				StdDraw.filledSquare(j + 0.5, height - i - 0.5, 0.5);
			}
		}
		StdDraw.show();
	}
}


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = { "/fileConvert" })
public class FileConverter extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private String destinationFile;
 
    public FileConverter() {
        super();
    }

    // This doPost method is executed when the user clicks the '' on the jsp page.
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
 throws ServletException, IOException {

		boolean hasError = false;
		String errorString = null;

		// Get the file location from the request parameters
		String metaFileLocation = request.getParameter("metaFileLocation");
		String dataFileLocation = request.getParameter("dataFileLocation");
		String destinationLocation = request
				.getParameter("destinationLocation");

		// validate all 3 provided paths.
		if (!isPathValid(metaFileLocation, true)) {
			// Return error string
			hasError = true;
			errorString = "Meta Data File Location is invalid. Please try again.";
		}
		if (!isPathValid(dataFileLocation, true)) {
			// Return error string
			hasError = true;
			errorString = "Data File Location is invalid. Please try again.";
		}
		if (!isPathValid(destinationLocation, false)) {
			// Return error string
			hasError = true;
			errorString = "Destination path is invalid. Please try again.";
		}

		// If error, forward back to FileConverter.jsp
		if (hasError) {
			// Store information in request attribute, before forward.
			request.setAttribute("errorString", errorString);

			// Go back to the same jsp with the error message.
			RequestDispatcher dispatcher = this.getServletContext()
					.getRequestDispatcher("/FileConverter.jsp");

			dispatcher.forward(request, response);
			return;
		}

		// All inputs verified. Begin processing.
		processInput(request, response, metaFileLocation,dataFileLocation, destinationLocation);
		// Success message
		String successString = "The CSV file has been created and placed in the specified destination folder. CSV File Name: "+destinationFile;
		request.setAttribute("successString", successString);
		// Go back to the same jsp with the error message.
		RequestDispatcher dispatcher = this.getServletContext()
				.getRequestDispatcher("/FileConverter.jsp");
		
		dispatcher.forward(request, response);

	}
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
    
	private boolean isPathValid(String path, boolean isFile) {
		try {
			if (path == null || path.length() <= 0) {
				return false;
			}

			if (isFile) {
				// The method is used to check the validity of a file path.
				return Files.exists(Paths.get(path));
			} else {
				// The method is used to check the validity of a directory path.
				return Files.isDirectory(Paths.get(path));
			}
		} catch (InvalidPathException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private void processInput(HttpServletRequest request,
			HttpServletResponse response, String metaFileLocation,
			String dataFileLocation, String destinationLocation){
		// process the meta data file
		ProcessMetaFile processMetaFile = new ProcessMetaFile(metaFileLocation);
		MetaDataDO metaDataDO = processMetaFile.processMetaFileData();
		// process and convert the data file
		ProcessDataFile processDataFile = new ProcessDataFile(dataFileLocation,
				destinationLocation, metaDataDO);
		destinationFile = processDataFile.processDataFile();
	}
}

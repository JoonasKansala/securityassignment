/*
    This is the first assigment for systems and security module. 
    Made by Joonas Känsälä in 23.3.2016
*/

package securityassignment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class FXMLDocumentController implements Initializable {
    
    static BigInteger keys[] = new BigInteger[3];
    static BigInteger encryptedMessage[] = new BigInteger[5000];
    static BigInteger encryptedFile[] = new BigInteger[50000000];
    static BigInteger encryptedInputFile[];
    static BigInteger decryptedMessage[] = new BigInteger[5000];
    static BigInteger decryptedFile[] = new BigInteger[50000000];
    static RSA rsa = new RSA();
    
    @FXML
    Button genKeys; 
    @FXML
    Button enMsg; 
    @FXML
    Button enFile; 
    @FXML
    Button deMsg; 
    @FXML
    Button deFile;
    
    @FXML
    TextField fieldBits;
    @FXML
    TextField fieldMsg; 
    @FXML
    TextField fieldEncrypted; 
    @FXML
    TextField fieldDecrypted;
    @FXML
    TextField fieldEnPath;
    @FXML
    TextField fieldDePath;
    
    @FXML
    Label keyPub;
    @FXML
    Label keyPriv;
    
    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        
        if(event.getSource() == genKeys){
            int bitCount = Integer.parseInt(fieldBits.getText());
    	    PrimeNumber p = new PrimeNumber(bitCount);
    	    PrimeNumber q = new PrimeNumber(bitCount);
    	    keys = rsa.calcKeys(p, q);
            keyPub.setText("" + keys[0]);
            keyPriv.setText("" + keys[2]);
                        
        }
        if(event.getSource() == enMsg){
            encryptedMessage = rsa.encrypt(fieldMsg.getText(), keys[0], keys[1]);
    	    		String encryptedMessageString = "";
    	    		for(int i=0; i<encryptedMessage.length; i++){
    	    			encryptedMessageString += (char) encryptedMessage[i].intValue();
    	    		}
    	    		fieldEncrypted.setText(encryptedMessageString);
        }
        if(event.getSource() == enFile){
            String inputFileString = "";
    	    		// Read in file content
    	    		try {
    	    			FileReader inputFile = new FileReader("E:/Java/SecurityAssignment/src/securityassignment/normalfile.txt");
    	    			Scanner in = new Scanner(inputFile);
    	    			while(in.hasNext())
                                    inputFileString = inputFileString + in.next()+ " ";
        	    		inputFile.close();
        	    		in.close();
    	    		} catch (FileNotFoundException ee) {
    	    			// TODO Auto-generated catch block
    	    			ee.printStackTrace();
    	    			System.exit(0);
    	    		} catch (IOException e1) {
						// TODO Auto-generated catch block
			e1.printStackTrace();
			}
    	    		
    	    		// Encrypt content
    	    		encryptedFile = rsa.encrypt(inputFileString, keys[0], keys[1]);
    	    		String encryptedFileString = "";
    	    		for(int i=0;i<encryptedFile.length;i++){
    	    			encryptedFileString += encryptedFile[i] + ",";
    	    		}
    	    		encryptedFileString += "0";
    	    		// Write encrypted content to file
    				PrintWriter printer;
    				File outputFile  = new File(fieldEnPath.getText());
    				try {
    					printer = new PrintWriter(outputFile);
    					printer.write(encryptedFileString);
        				printer.flush();	
        				printer.close();
    				} catch (FileNotFoundException ee) {
    					// TODO Auto-generated catch block
    					ee.printStackTrace();
    				}
        }
        if(event.getSource() == deMsg){
            decryptedMessage = rsa.decrypt(encryptedMessage, keys[0], keys[2]);
     	    		String decryptedMessageString = "";
     	    		for(int i=0;i<decryptedMessage.length;i++){
     	    			decryptedMessageString+= ((char) decryptedMessage[i].intValue());			
     	    		}
     	    		fieldDecrypted.setText(decryptedMessageString);
        }
        if(event.getSource() == deFile){
            String encryptedFileString = "";
    	    // Read in file content
    	    try {
    	    FileReader inputFile = new FileReader(fieldEnPath.getText());
    	    Scanner in = new Scanner(inputFile);
    	    while(in.hasNext()){
    	    				encryptedFileString = encryptedFileString + in.next();
    	    			}
        	    		inputFile.close();
        	    		in.close();
    	    		} catch (FileNotFoundException ee) {
    	    			// TODO Auto-generated catch block
    	    			ee.printStackTrace();
    	    			System.exit(0);
    	    		} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}	    			
    	    		String messageParts[] = encryptedFileString.split(",");
    	    		encryptedInputFile = new BigInteger[messageParts.length];
    	    		
	    			for(int i=0;i<messageParts.length;i++)
		    				encryptedInputFile[i] = new BigInteger(messageParts[i]);
	    			
	    			// Decrypt content
     	    		decryptedFile = rsa.decrypt(encryptedInputFile, keys[0], keys[2]);
     	    		
     	    		String decryptedMessageString = "";
     	    		for(int i=0;i<decryptedFile.length-1;i++) // Lengt-1 since last part is 0
     	    			decryptedMessageString+= ((char) decryptedFile[i].intValue());			

     	    		// Write decrypted content to file
    				PrintWriter printer;
    				File outputFile  = new File(fieldDePath.getText());
    				try {
    					printer = new PrintWriter(outputFile);
    					printer.write(decryptedMessageString);
        				printer.flush();	
        				printer.close();
    				} catch (FileNotFoundException ee) {
    					// TODO Auto-generated catch block
    					ee.printStackTrace();
    				}	
     	    	}
     	    	
        }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       
    }
}
    

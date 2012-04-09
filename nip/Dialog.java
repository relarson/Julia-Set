package nip;

import java.lang.reflect.InvocationTargetException;

import javax.swing.JOptionPane;

/**
 * 
 * The Dialog class provides methods for showing dialog boxes to the user and getting typed user input.
 * 
 * @author Kenneth J. Goldman<BR>
 * Created Jul 5, 2005
 */
public class Dialog extends javax.swing.JOptionPane{

	private static final long serialVersionUID = 1L;

	/**
	 * Shows the given message to the user.
	 * @param message the message to show
	 */
	public static void showMessage(String message) {
		showMessageDialog(null,message);
	}
	
	/**
	 * Shows an error message to the user.
	 * @param message the error message to show
	 */
	public static void showError(String message) {
		showMessageDialog(null,message, "Error", ERROR_MESSAGE);
	}
	
	
	static void reportException(Throwable e, java.lang.reflect.Method m, boolean consoleOnly) {
		if (e instanceof InvocationTargetException)
			e = e.getCause();
		if (consoleOnly)
			System.err.println(e.getMessage());
		else {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "The following exception occurred in " + m.getName() + ":\n" +
				e.getClass().getName() + "(" + e.getMessage() + ") \n\n(See console for details.)", "Unhandled Exception in "+ m.getName(), JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Asks the user a yes/no question.
	 * @param question the question to ask the user
	 * @return true if the user answers "yes," false if the user answers "no"
	 */
	public static boolean getApproval(String question) {
		return YES_OPTION == showConfirmDialog(null,question,"Please answer yes or no.",
				YES_NO_OPTION);
	}
	
	/**
	 * Prompts the user for a textual response.
	 * @param prompt the prompt/question to display to the user
	 * @return the text that the user typed
	 */
	public static String getText(String prompt) {
		String result = javax.swing.JOptionPane.showInputDialog(null,prompt);
		if (result == null)
			result = "";
		return result.trim();
	}
	
	/**
	 * Prompts the user for a textual response, with a default answer displayed.
	 * @param prompt the prompt/question to display to the user
	 * @param defaultAnswer the initial text to display in the answer box
	 * @return the text that the user typed
	 */
	public static String getText(String prompt, String defaultAnswer) {
		String result = javax.swing.JOptionPane.showInputDialog(null,prompt,defaultAnswer);
		if (result == null)
			result = "";
		return result.trim();
	}
	
	/**
	 * Prompts the user for an integer response.
	 * If the user does not type an integer, an error message is shown and then the user is asked again.
	 * @param prompt the prompt/question to display to the user
	 * @return the integer value that the user typed
	 */
	public static int getInteger(String prompt) {
		String result = showInputDialog(null,
				prompt,
				"Please enter an integer value.",
				QUESTION_MESSAGE);
		try {
			return Integer.parseInt(result);
		} catch (Exception e) {
			showError("An integer is required.");
			return getInteger(prompt);
		}
	}

	/**
	 * Prompts the user for a number.
	 * If the user does not type an number, an error message is shown and then the user is asked again.
	 * @param prompt the prompt/question to display to the user
	 * @return the numeric value that the user typed
	 */
	public static double getDouble(String prompt) {
		String result = showInputDialog(null,
				prompt,
				"Please enter a number.",
				QUESTION_MESSAGE);
		try {
			return Double.parseDouble(result);
		} catch (Exception e) {
			showError("A number is required.");
			return getDouble(prompt);
		}
	}

	public static void main(String[] args) {
		// Examples:
		String name = getText("What's your name?");
		System.out.println(getText("What's your name?",name));
		System.out.println(getApproval("Yes?"));
		System.out.println(getInteger("How many?"));
		System.out.println(getDouble("What value?"));
		showMessage("Thank you.");
		System.exit(0);
	}
}

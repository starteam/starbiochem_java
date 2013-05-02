package app;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import star.annotations.Handles;
import star.annotations.SignalComponent;
import star.events.common.DistributionExceptionRaiser;
import app.Version;
import events.ErrorDialogRaiser;
import star.ui.feedback.FeedbackDialog;
import utils.UIHelpers;
import app.Messages;

/**
 * Displays Error Dialog raised from other components
 * 
 * @author ceraj
 */
@SignalComponent()
public class ErrorDialogHandler extends ErrorDialogHandler_generated {

	private static final long serialVersionUID = 1L;

	@Override
	@Handles(raises = {})
	void openErrorDialog(ErrorDialogRaiser r) {
		try {
			Exception errorMessage = r.getErrorMessage();
			if (errorMessage != null
					&& errorMessage.getCause() != null
					&& errorMessage.getCause().getCause() != null
					&& errorMessage.getCause().getCause() instanceof FileNotFoundException) {
				JOptionPane.showMessageDialog(
						UIHelpers.getFrame(UIHelpers.getComponent(this)),
						new JLabel(errorMessage.getLocalizedMessage()));
			} else if (errorMessage != null
					&& errorMessage.getCause() != null
					&& errorMessage.getCause().getCause() != null
					&& errorMessage.getCause().getCause() instanceof IOException) {
				StringBuilder sb = new StringBuilder();
				sb.append("<html><body>" + Messages.getString("ErrorDialogHandler.1") + "<br><br>" + Messages.getString("ErrorDialogHandler.3") +"<br><b>"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
				sb.append(r.getErrorMessage().getLocalizedMessage() + "</b>"); //$NON-NLS-1$
				sb.append("<br><br>" + Messages.getString("ErrorDialogHandler.7")); //$NON-NLS-1$ //$NON-NLS-2$
				sb.append("<br>" + Messages.getString("ErrorDialogHandler.9")); //$NON-NLS-1$ //$NON-NLS-2$
				String[] options = new String[] { Messages.getString("ErrorDialogHandler.10"), Messages.getString("ErrorDialogHandler.11") }; //$NON-NLS-1$ //$NON-NLS-2$
				int ret = JOptionPane
						.showOptionDialog(UIHelpers.getFrame(UIHelpers
								.getComponent(this)),
								new JLabel(sb.toString()), Messages.getString("ErrorDialogHandler.12"), //$NON-NLS-1$
								JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE, null, options,
								options[1]);
				if (ret == JOptionPane.YES_OPTION) {
					FeedbackDialog dialog = new FeedbackDialog(
							UIHelpers.getFrame(UIHelpers.getComponent(this)),
							app.Version.getProject(), app.Version
									.getBuildDate().toString());
					dialog.reportBug(r.getErrorMessage().getCause(),
							String.valueOf(Version.getBuildDate()));
					dialog.pack();
					dialog.setVisible(true);

				}
			} else if (errorMessage != null && errorMessage.getCause() == null) {
				JOptionPane.showMessageDialog(
						UIHelpers.getFrame(UIHelpers.getComponent(this)),
						new JLabel(errorMessage.getLocalizedMessage()));
			} else {
				StringBuilder sb = new StringBuilder();
				sb.append("<html><body><b>"); //$NON-NLS-1$
				sb.append(r.getErrorMessage().getLocalizedMessage() + "</b>"); //$NON-NLS-1$
				sb.append("<br>" + Messages.getString("ErrorDialogHandler.16")); //$NON-NLS-1$ //$NON-NLS-2$
				sb.append("<br>" + Messages.getString("ErrorDialogHandler.18")); //$NON-NLS-1$ //$NON-NLS-2$
				String[] options = new String[] { Messages.getString("ErrorDialogHandler.19"), Messages.getString("ErrorDialogHandler.20") }; //$NON-NLS-1$ //$NON-NLS-2$
				int ret = JOptionPane
						.showOptionDialog(UIHelpers.getFrame(UIHelpers
								.getComponent(this)),
								new JLabel(sb.toString()), Messages.getString("ErrorDialogHandler.21"), //$NON-NLS-1$
								JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE, null, options,
								options[1]);
				if (ret == JOptionPane.YES_OPTION) {
					FeedbackDialog dialog = new FeedbackDialog(
							UIHelpers.getFrame(UIHelpers.getComponent(this)),
							app.Version.getProject(), app.Version
									.getBuildDate().toString());
					dialog.reportBug(r.getErrorMessage().getCause(),
							String.valueOf(Version.getBuildDate()));
					dialog.pack();
					dialog.setVisible(true);

				}
			}

		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	@Override
	@Handles(raises = { ErrorDialogRaiser.class })
	void openErrorDialogOnDistributionException(DistributionExceptionRaiser r) {
		errorMessage = new Exception(r.getException());
		raise_ErrorDialogEvent();
	}

	transient Exception errorMessage = null;

	public Exception getErrorMessage() {
		return errorMessage;
	}

}
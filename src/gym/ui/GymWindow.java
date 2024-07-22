package src.gym.ui;

import javax.swing.*;

import src.gym.delegates.GUIGymDelegate;
import src.gym.model.Member.MemberModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Date;

public class GymWindow extends JFrame implements ActionListener  {
	
	private final int FIELD_WIDTH = 15;
    private GUIGymDelegate delegate;

    private JTextField MIDField;
    private JTextField nameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JTextField clearanceField;

    private JTextField SType;

    private JTextField MIDUpdateField;
    private JTextField phoneUpdateField;
    private JTextField emailUpdateField;

    private JTextField clearanceSelectField;
    
    private JTextField attributesField;
    private JTextField tableNameField;

    private JTextField sessionDateField;

	private JLabel insertStatus;
	private JLabel deleteStatus;
	private JLabel updateStatus;
	private JLabel selectStatus;
	private JLabel projectionStatus;
	private JLabel joinStatus;
	private JLabel aggGBStatus;
	private JLabel aggHavingStatus;
	private JLabel nestedGBStatus;
	private JLabel divisionStatus;

	private JLabel goStatus;


    JButton insertButton;
    JButton deleteButton;
    JButton updateButton;
    JButton selectButton;
    JButton projectButton;
    JButton joinButton;
    JButton groupByButton;
    JButton havingButton;
    JButton nestedGroupByButton;
    JButton divisionButton;

	JButton goButton;

    public GymWindow() {
        super("Gym");
    }

    public void showFrame(GUIGymDelegate delegate) {
        this.delegate = delegate;

        // INSERT OPERATION SECTION
        JLabel INSERTOPLabel = new JLabel("Insert New Member");

        JLabel MID = new JLabel("Enter MID: ");
        JLabel memberName = new JLabel("Enter Name: ");
		JLabel memberEmail = new JLabel("Enter Email: ");
		JLabel memberPhone = new JLabel("Enter Phone: ");
        JLabel memberClearance = new JLabel("Enter Clearance: ");

        MIDField = new JTextField(FIELD_WIDTH);
        nameField = new JTextField(FIELD_WIDTH);
        emailField = new JTextField(FIELD_WIDTH);
        phoneField = new JTextField(FIELD_WIDTH);
        clearanceField = new JTextField(FIELD_WIDTH);

        insertButton = new JButton("Insert Member");

		insertStatus = new JLabel("");

        JPanel contentPane = new JPanel();
		this.setContentPane(contentPane);

        GridBagLayout gb = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();

        contentPane.setLayout(gb);
		contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		contentPane.setPreferredSize(new Dimension(700,700));

        c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(0, 0, 0, 0);
		gb.setConstraints(INSERTOPLabel, c);
		contentPane.add(INSERTOPLabel);

        // MID
		c.gridwidth = GridBagConstraints.RELATIVE;
		c.insets = new Insets(0, 0, 0, 0);
		gb.setConstraints(MID, c);
		contentPane.add(MID);

		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(0, 0, 0, 0);
		gb.setConstraints(MIDField, c);
		contentPane.add(MIDField);

		// NAME
		c.gridwidth = GridBagConstraints.RELATIVE;
		c.insets = new Insets(0, 0, 0, 0);
		gb.setConstraints(memberName, c);
		contentPane.add(memberName);

		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(0, 0, 0, 0);
		gb.setConstraints(nameField, c);
		contentPane.add(nameField);

        // EMAIL
		c.gridwidth = GridBagConstraints.RELATIVE;
		c.insets = new Insets(0, 0, 0, 0);
		gb.setConstraints(memberEmail, c);
		contentPane.add(memberEmail);

		c.gridwidth = GridBagConstraints.REMAINDER ;
		c.insets = new Insets(0, 0, 0, 0);
		gb.setConstraints(emailField, c);
		contentPane.add(emailField);

        // PHONE
		c.gridwidth = GridBagConstraints.RELATIVE;
		c.insets = new Insets(0, 0, 0, 0);
		gb.setConstraints(memberPhone, c);
		contentPane.add(memberPhone);

		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(0, 0, 0, 0);
		gb.setConstraints(phoneField, c);
		contentPane.add(phoneField);

        // CLEARANCE
		c.gridwidth = GridBagConstraints.RELATIVE;
		c.insets = new Insets(0, 0, 0, 0);
		gb.setConstraints(memberClearance, c);
		contentPane.add(memberClearance);

		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(0, 0, 0, 0);
		gb.setConstraints(clearanceField, c);
		contentPane.add(clearanceField);

		// place the insert button
		c.gridwidth = GridBagConstraints.RELATIVE;
		c.insets = new Insets(0, 0, 0, 0);
		c.anchor = GridBagConstraints.CENTER;
		gb.setConstraints(insertButton, c);
		contentPane.add(insertButton);

		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(0, 0, 0, 0);
		gb.setConstraints(insertStatus, c);
		contentPane.add(insertStatus);

        // register login button with action event handler
		insertButton.addActionListener(this);

        // INSERT OPERATION SECTION END ------------------------------------------------------------------------------------------------
        // DELETE OPERATION SECTION
        JLabel DELETEOPLabel = new JLabel("Delete Staff By Type");

        JLabel SID = new JLabel("Enter Staff Type: ");
        SType = new JTextField(FIELD_WIDTH);

        deleteButton = new JButton("Delete Staff");

		deleteStatus = new JLabel("");

        c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(0, 0, 0, 0);
		gb.setConstraints(DELETEOPLabel, c);
		contentPane.add(DELETEOPLabel);

        // SID
		c.gridwidth = GridBagConstraints.RELATIVE;
		c.insets = new Insets(0, 0, 0, 0);
		gb.setConstraints(SID, c);
		contentPane.add(SID);

		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(0, 0, 0, 0);
		gb.setConstraints(SType, c);
		contentPane.add(SType);

        // DELETE BUTTON
        c.gridwidth = GridBagConstraints.RELATIVE;
		c.insets = new Insets(0, 0, 0, 0);
		c.anchor = GridBagConstraints.CENTER;
		gb.setConstraints(deleteButton, c);
		contentPane.add(deleteButton);

		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(0, 0, 0, 0);
		gb.setConstraints(deleteStatus, c);
		contentPane.add(deleteStatus);

        // register login button with action event handler
		deleteButton.addActionListener(this);

        // DELETE OPERATION SECTION END ------------------------------------------------------------------------------------------------
        // UPDATE OPERATION SECTION
        JLabel UPDATEOPLabel = new JLabel("Update Member Phone/Email");

        JLabel MIDUpdate = new JLabel("Enter MID: ");
        JLabel memberPhoneUpdate = new JLabel("Enter Phone: ");
		JLabel memberEmailUpdate = new JLabel("Enter Email: ");

        MIDUpdateField = new JTextField(FIELD_WIDTH);
        phoneUpdateField = new JTextField(FIELD_WIDTH);
        emailUpdateField = new JTextField(FIELD_WIDTH);

        updateButton = new JButton("Update Member");

		updateStatus = new JLabel("");

        c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(0, 0, 0, 0);
		gb.setConstraints(UPDATEOPLabel, c);
		contentPane.add(UPDATEOPLabel);

        // MID
		c.gridwidth = GridBagConstraints.RELATIVE;
		c.insets = new Insets(0, 0, 0, 0);
		gb.setConstraints(MIDUpdate, c);
		contentPane.add(MIDUpdate);

		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(0, 0, 0, 0);
		gb.setConstraints(MIDUpdateField, c);
		contentPane.add(MIDUpdateField);

        // PHONE
		c.gridwidth = GridBagConstraints.RELATIVE;
		c.insets = new Insets(0, 0, 0, 0);
		gb.setConstraints(memberPhoneUpdate, c);
		contentPane.add(memberPhoneUpdate);

		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(0, 0, 0, 0);
		gb.setConstraints(phoneUpdateField, c);
		contentPane.add(phoneUpdateField);

        // EMAIL
		c.gridwidth = GridBagConstraints.RELATIVE;
		c.insets = new Insets(0, 0, 0, 0);
		gb.setConstraints(memberEmailUpdate, c);
		contentPane.add(memberEmailUpdate);

		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(0, 0, 0, 0);
		gb.setConstraints(emailUpdateField, c);
		contentPane.add(emailUpdateField);

        // UPDATE BUTTON
        c.gridwidth = GridBagConstraints.RELATIVE;
		c.insets = new Insets(0, 0, 0, 0);
		c.anchor = GridBagConstraints.CENTER;
		gb.setConstraints(updateButton, c);
		contentPane.add(updateButton);

		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(0, 0, 0, 0);
		gb.setConstraints(updateStatus, c);
		contentPane.add(updateStatus);


        // register login button with action event handler
		updateButton.addActionListener(this);
        // UPDATE OPERATION SECTION END ------------------------------------------------------------------------------------------------
        // SELECTION OPERATION
        JLabel SELECTOPLabel = new JLabel("Select Members with x Clearance");

        JLabel clearanceSelect = new JLabel("Enter Clearance Level: ");
        clearanceSelectField = new JTextField(FIELD_WIDTH);

        selectButton = new JButton("Select Members");

		selectStatus = new JLabel("");

        c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(0, 0, 0, 0);
		gb.setConstraints(SELECTOPLabel, c);
		contentPane.add(SELECTOPLabel);

        // SID
		c.gridwidth = GridBagConstraints.RELATIVE;
		c.insets = new Insets(0, 0, 0, 0);
		gb.setConstraints(clearanceSelect, c);
		contentPane.add(clearanceSelect);

		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(0, 0, 0, 0);
		gb.setConstraints(clearanceSelectField, c);
		contentPane.add(clearanceSelectField);

        // SELECT BUTTON
        c.gridwidth = GridBagConstraints.RELATIVE;
		c.insets = new Insets(0, 0, 0, 0);
		c.anchor = GridBagConstraints.CENTER;
		gb.setConstraints(selectButton, c);
		contentPane.add(selectButton);
		
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(0, 0, 0, 0);
		gb.setConstraints(selectStatus, c);
		contentPane.add(selectStatus);

        // register login button with action event handler
		selectButton.addActionListener(this);
        // SELECT OPERATION SECTION END ------------------------------------------------------------------------------------------------
        // PROJECT OPERATION SECTION
        JLabel PROJECTOPLabel = new JLabel("Extract Data Columns");

        JLabel attributes = new JLabel("List attributes (comma sperated, no spaces): ");
        // JLabel extra = new JLabel("comma sperated, no spaces");
        JLabel tableName = new JLabel("Table Name: ");

        attributesField = new JTextField(FIELD_WIDTH);
        tableNameField = new JTextField(FIELD_WIDTH);
        
        projectButton = new JButton("Project Attributes");

		projectionStatus = new JLabel("");

        c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(0, 0, 0, 0);
		gb.setConstraints(PROJECTOPLabel, c);
		contentPane.add(PROJECTOPLabel);

        // ATTRIBUTES
		c.gridwidth = GridBagConstraints.RELATIVE;
		c.insets = new Insets(0, 0, 0, 0);
		gb.setConstraints(attributes, c);
		contentPane.add(attributes);

		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(0, 0, 0, 0);
		gb.setConstraints(attributesField, c);
		contentPane.add(attributesField);

        // c.gridwidth = GridBagConstraints.REMAINDER;
		// c.insets = new Insets(0, 0, 0, 0);
		// gb.setConstraints(extra, c);
		// contentPane.add(extra);
        // TABLE NAME
		c.gridwidth = GridBagConstraints.RELATIVE;
		c.insets = new Insets(0, 0, 0, 0);
		gb.setConstraints(tableName, c);
		contentPane.add(tableName);

		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(0, 0, 0, 0);
		gb.setConstraints(tableNameField, c);
		contentPane.add(tableNameField);

        // DELETE BUTTON
        c.gridwidth = GridBagConstraints.RELATIVE;
		c.insets = new Insets(0, 0, 0, 0);
		c.anchor = GridBagConstraints.CENTER;
		gb.setConstraints(projectButton, c);
		contentPane.add(projectButton);

		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(0, 0, 0, 0);
		gb.setConstraints(projectionStatus, c);
		contentPane.add(projectionStatus);

        // register login button with action event handler
		projectButton.addActionListener(this);
        // PROJECT OPERATION SECTION END ------------------------------------------------------------------------------------------------
        // JOIN OPERATION SECTION
        JLabel JOINOPLabel = new JLabel("Find Members Present on Date");

        JLabel sessionDate = new JLabel("Enter Session Date (yyyy-mm-dd): ");
        sessionDateField = new JTextField(FIELD_WIDTH);

        joinButton = new JButton("Search Members");

		joinStatus = new JLabel("");

        c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(0, 0, 0, 0);
		gb.setConstraints(JOINOPLabel, c);
		contentPane.add(JOINOPLabel);

        // Session Date
		c.gridwidth = GridBagConstraints.RELATIVE;
		c.insets = new Insets(0, 0, 0, 0);
		gb.setConstraints(sessionDate, c);
		contentPane.add(sessionDate);

		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(0, 0, 0, 0);
		gb.setConstraints(sessionDateField, c);
		contentPane.add(sessionDateField);

        // JOIN BUTTON
        c.gridwidth = GridBagConstraints.RELATIVE;
		c.insets = new Insets(0, 0, 0, 0);
		c.anchor = GridBagConstraints.CENTER;
		gb.setConstraints(joinButton, c);
		contentPane.add(joinButton);

		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(0, 0, 0, 0);
		gb.setConstraints(joinStatus, c);
		contentPane.add(joinStatus);

        // register login button with action event handler
		joinButton.addActionListener(this);
        // JOIN OPERATION SECTION END ------------------------------------------------------------------------------------------------
        // AGG GROUP BY OPERATION SECTION
        JLabel AGGGROUPBYOPLabel = new JLabel("Get Equipment Quantities AGG GB");
        groupByButton = new JButton("Get Equipment Quantities");

		aggGBStatus = new JLabel("");

        c.gridwidth = GridBagConstraints.RELATIVE;
		c.insets = new Insets(0, 0, 0, 0);
		gb.setConstraints(AGGGROUPBYOPLabel, c);
		contentPane.add(AGGGROUPBYOPLabel);

        c.gridwidth = GridBagConstraints.RELATIVE;
		c.insets = new Insets(0, 0, 0, 0);
		c.anchor = GridBagConstraints.CENTER;
		gb.setConstraints(groupByButton, c);
		contentPane.add(groupByButton);

		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(0, 0, 0, 0);
		gb.setConstraints(aggGBStatus, c);
		contentPane.add(aggGBStatus);

        groupByButton.addActionListener(this);
        // AGG GROUP BY OPERATION SECTION END ------------------------------------------------------------------------------------------------
        // AGG HAVING OPERATION SECTION
        JLabel AGGHAVINGOPLabel = new JLabel("Get Member >1 Session AGG HAVING");
        havingButton = new JButton("Get Members");

		aggHavingStatus = new JLabel("");

        c.gridwidth = GridBagConstraints.RELATIVE;
		c.insets = new Insets(0, 0, 0, 0);
		gb.setConstraints(AGGHAVINGOPLabel, c);
		contentPane.add(AGGHAVINGOPLabel);

        c.gridwidth = GridBagConstraints.RELATIVE;
		c.insets = new Insets(0, 0, 0, 0);
		c.anchor = GridBagConstraints.CENTER;
		gb.setConstraints(havingButton, c);
		contentPane.add(havingButton);

		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(0, 0, 0, 0);
		gb.setConstraints(aggHavingStatus, c);
		contentPane.add(aggHavingStatus);

        havingButton.addActionListener(this);
        // AGG HAVING OPERATION SECTION END ------------------------------------------------------------------------------------------------
        // NESTED GROUP BY OPERATION SECTION
        JLabel NESTGROUPBYOPLabel = new JLabel("Find number of long sessions NESTED GB");
        nestedGroupByButton = new JButton("Get Sessions");

		nestedGBStatus = new JLabel("");

        c.gridwidth = GridBagConstraints.RELATIVE;
		c.insets = new Insets(0, 0, 0, 0);
		gb.setConstraints(NESTGROUPBYOPLabel, c);
		contentPane.add(NESTGROUPBYOPLabel);

        c.gridwidth = GridBagConstraints.RELATIVE;
		c.insets = new Insets(0, 0, 0, 0);
		c.anchor = GridBagConstraints.CENTER;
		gb.setConstraints(nestedGroupByButton, c);
		contentPane.add(nestedGroupByButton);

		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(0, 0, 0, 0);
		gb.setConstraints(nestedGBStatus, c);
		contentPane.add(nestedGBStatus);


        nestedGroupByButton.addActionListener(this);
        // NESTED GROUP BY OPERATION SECTION END ------------------------------------------------------------------------------------------------
        // DIVISION OPERATION SECTION
        JLabel DIVISIONOPLabel = new JLabel("Find staff who have worked on all floors");
        divisionButton = new JButton("Get Staff");

		divisionStatus = new JLabel("");

        c.gridwidth = GridBagConstraints.RELATIVE;
		c.insets = new Insets(0, 0, 0, 0);
		gb.setConstraints(DIVISIONOPLabel, c);
		contentPane.add(DIVISIONOPLabel);

        c.gridwidth = GridBagConstraints.RELATIVE;
		c.insets = new Insets(0, 0, 0, 0);
		c.anchor = GridBagConstraints.CENTER;
		gb.setConstraints(divisionButton, c);
		contentPane.add(divisionButton);

		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(0, 0, 0, 0);
		gb.setConstraints(divisionStatus, c);
		contentPane.add(divisionStatus);

        divisionButton.addActionListener(this);

//		JLabel goLabel = new JLabel("Show All Data");
//		goButton = new JButton("GO!");
//
//		goStatus = new JLabel("");
//
//		c.gridwidth = GridBagConstraints.RELATIVE;
//		c.insets = new Insets(0, 0, 0, 0);
//		gb.setConstraints(goLabel, c);
//		contentPane.add(goLabel);
//
//		c.gridwidth = GridBagConstraints.RELATIVE;
//		c.insets = new Insets(0, 0, 0, 0);
//		c.anchor = GridBagConstraints.CENTER;
//		gb.setConstraints(goButton, c);
//		contentPane.add(goButton);
//
//		c.gridwidth = GridBagConstraints.REMAINDER;
//		c.insets = new Insets(0, 0, 0, 0);
//		gb.setConstraints(goStatus, c);
//		contentPane.add(goStatus);
//
//		goButton.addActionListener(this);
        // DIVISION OPERATION SECTION END

        // anonymous inner class for closing the window
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				delegate.transactionsFinished();
			}
		});

		// size the window to obtain a best fit for the components
		this.pack();

		// center the frame
		Dimension d = this.getToolkit().getScreenSize();
		Rectangle r = this.getBounds();
		this.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );

		// make the window visible
		 this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == insertButton) {
			try {
				delegate.insertMember(new MemberModel(Integer.parseInt(MIDField.getText()), nameField.getText(), phoneField.getText() , emailField.getText(), Integer.parseInt(clearanceField.getText())));
				insertStatus.setText("Insert Complete!");
			} catch (Exception except) {
				insertStatus.setText("Insert Failed!");
			}
            
        } else if (e.getSource() == deleteButton) {
			try {
				delegate.deleteStaff(SType.getText());
				deleteStatus.setText("Delete Complete!");
			} catch (Exception except) {
				deleteStatus.setText("Delete Failed!");
			}
        } else if (e.getSource() == updateButton) {
			try {
				delegate.updateMember(Integer.parseInt(MIDUpdateField.getText()), phoneUpdateField.getText(), emailUpdateField.getText());
				updateStatus.setText("Update Complete!");
			} catch (Exception except) {
				updateStatus.setText("Update Failed!");
			}
        } else if (e.getSource() == selectButton) {
			try {
				String str = delegate.showMembersWith(Integer.parseInt(clearanceSelectField.getText()));
				selectStatus.setText("Select Complete!");
				testWindow testWindow = new testWindow();
				testWindow.updateInfo(str);
				testWindow.showFrame();
			} catch (Exception except) {
				selectStatus.setText("Select Failed!");
			}
        } else if (e.getSource() == projectButton) {
			try {
				String[] attributesArr = attributesField.getText().split(",");
				String str = delegate.projectFrom(attributesArr, tableNameField.getText());
				projectionStatus.setText("Projection Complete!");
				testWindow testWindow = new testWindow();
				testWindow.updateInfo(str);
				testWindow.showFrame();
			} catch (Exception except) {
				projectionStatus.setText("Projection Failed!");
			}
        } else if (e.getSource() == joinButton) {
			try {
				String str=sessionDateField.getText();  
				Date date=Date.valueOf(str);
				String str2 = delegate.showMembersOnDate(date);
				joinStatus.setText("Join Complete!");
				testWindow testWindow = new testWindow();
				testWindow.updateInfo(str2);
				testWindow.showFrame();
			} catch (Exception except) {
				joinStatus.setText("Join Failed!");
			}
        } else if (e.getSource() == groupByButton) {
			try {
				String str = delegate.getEquipmentAndTheirQuantities();
				aggGBStatus.setText("Complete!");
				testWindow testWindow = new testWindow();
				testWindow.updateInfo(str);
				testWindow.showFrame();
			} catch (Exception except) {
				aggGBStatus.setText("Failed!");
			}
        } else if (e.getSource() == havingButton) {
			try {
				String str = delegate.getMembersWithMoreThanOneSessionAndTheirSessionInfo();
				aggHavingStatus.setText("Complete!");
				testWindow testWindow = new testWindow();
				testWindow.updateInfo(str);
				testWindow.showFrame();
			} catch (Exception except) {
				aggHavingStatus.setText("Failed!");
			}
        } else if (e.getSource() == nestedGroupByButton) {
			try {
				String str = delegate.getNumberOfLongSessionsMembersWithMoreThanOneLongSessionsHaveHad();
				nestedGBStatus.setText("Complete!");
				testWindow testWindow = new testWindow();
				testWindow.updateInfo(str);
				testWindow.showFrame();
			} catch (Exception except) {
				nestedGBStatus.setText("Failed!");
			}
        } else if (e.getSource() == divisionButton) {
			try {
				String str = delegate.getStaffWhoHaveWorkedOnAllFloors();
				divisionStatus.setText("Complete!");

				testWindow testWindow = new testWindow();
				testWindow.updateInfo(str);
				testWindow.showFrame();
			} catch (Exception except) {
				divisionStatus.setText("Failed!");
			}
        } else if (e.getSource() == goButton) {
			try {
				String str = delegate.showAllData();
				goStatus.setText("Complete!");

				testWindow testWindow = new testWindow();
				testWindow.updateInfo(str);
				testWindow.showFrame();
			} catch (Exception except) {
				goStatus.setText("Failed!");
			}
		}
    }
    
}

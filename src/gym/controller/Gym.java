package src.gym.controller;

import src.gym.database.DatabaseConnectionHandler;
import src.gym.delegates.GUIGymDelegate;
import src.gym.delegates.LoginWindowDelegate;
import src.gym.model.*;
import src.gym.model.Constants.*;
import src.gym.model.Equipment.*;
import src.gym.model.FitnessClass.*;
import src.gym.model.Member.*;
import src.gym.model.PersonalTrainer.*;
import src.gym.model.Session.*;
import src.gym.model.Staff.*;
import src.gym.ui.GymWindow;
import src.gym.ui.LoginWindow;

import java.sql.Date;
import java.sql.SQLException;
import java.util.*;

import oracle.net.aso.e;

/**
 * This is the main controller class that will orchestrate everything.
 *
 * Template taken from
 * https://github.students.cs.ubc.ca/CPSC304/CPSC304_Java_Project
 */
public class Gym implements LoginWindowDelegate, GUIGymDelegate {
	private DatabaseConnectionHandler dbHandler = null;
	private LoginWindow loginWindow = null;
	private GymWindow gymWindow = null;

	public Gym() {
		dbHandler = new DatabaseConnectionHandler();
	}

	private void start() {
		loginWindow = new LoginWindow();
		loginWindow.showFrame(this);
	}

	/**
	 * LoginWindowDelegate Implementation
	 * 
	 * connects to Oracle database with supplied username and password
	 */
	public void login(String username, String password) {
		// boolean didConnect = dbHandler.login(username, password);

		// if (didConnect) {
		// // Once connected, remove login window and start text transaction flow
		// loginWindow.dispose();

		// GUITransactions transaction = new GUITransactions();
		// transaction.setupDatabase(this);
		// transaction.showMainMenu(this);
		// } else {
		// loginWindow.handleLoginFailed();

		// if (loginWindow.hasReachedMaxLoginAttempts()) {
		// loginWindow.dispose();
		// System.out.println("You have exceeded your number of allowed attempts");
		// System.exit(-1);
		// }
		// }

		boolean didConnect = dbHandler.login(username, password);
		if (didConnect) {
			// Connected, so remove login window and start gym window
			loginWindow.dispose();
			dbHandler.databaseSetup();
			gymWindow = new GymWindow();
			gymWindow.showFrame(this);
		} else {
			loginWindow.handleLoginFailed();
			if (loginWindow.hasReachedMaxLoginAttempts()) {
				loginWindow.dispose();
				System.out.println("THREE STRIKES UR OUT, ALLOWED LOGIN ATTEMPT EXCEEDED BYE!");
			}
		}

	}

	/**
	 * TermainalTransactionsDelegate Implementation
	 * 
	 * Insert a branch with the given info
	 */
    public void insertMember(MemberModel model) throws SQLException {
		System.out.println(model.getEmail());
		dbHandler.insertMember(model);
	}

    /**
	 * Terminal TransactionsDelegate Implementation
	 * 
	 * Delete branch with given branch ID.
	 */ 
    public void deleteStaff(String SType) throws SQLException {
    	dbHandler.deleteStaff(SType);
    }
    
    /**
	 * TermainalTransactionsDelegate Implementation
	 * 
	 * Update the branch name for a specific ID
	 */

    public void updateMember(int MID, String phone, String email) throws SQLException {
		System.out.println(MID + phone + email);
		dbHandler.updateMember(MID, phone, email);
	}

	/**
	 * TermainalTransactionsDelegate Implementation
	 *
	 * TODO
	 */

	public String projectFrom(String[] attributes, String tableName) throws SQLException {
//		return dbHandler.projectFrom(attributes,tableName);
		tableName = tableName.toUpperCase();
//		Set<String> attributeSet = new HashSet<>(List.of(attributes));
//
		switch (tableName) {
			case "REQUIRES":
				return dbHandler.projectFromRequires(attributes);
			case "USES":
				return dbHandler.projectFromUses(attributes);
			case "TRAINS":
				return dbHandler.projectFromTrains(attributes);
			case "WORKSON":
				return dbHandler.projectFromWorksOn(attributes);
			case "LEADS":
				return dbHandler.projectFromLeads(attributes);
			case "UTILIZES":
				return dbHandler.projectFromUtilizes(attributes);
			case "OCCURSIN":
				return dbHandler.projectFromOccursIn(attributes);
			case "ATTENDS":
				return dbHandler.projectFromAttends(attributes);
			case "PERSONALTRAINER2":
				return dbHandler.projectFromPersonalTrainer2(attributes);
			case "PERSONALTRAINER1":
				return dbHandler.projectFromPersonalTrainer1(attributes);
			case "STAFF2":
				return dbHandler.projectFromStaff2(attributes);
			case "STAFF1":
				return dbHandler.projectFromStaff1(attributes);
			case "FITNESSCLASS2":
				return dbHandler.projectFromFitnessClass2(attributes);
			case "FITNESSCLASS1":
				return dbHandler.projectFromFitnessClass1(attributes);
			case "SESSION2":
				return dbHandler.projectFromSession2(attributes);
			case "SESSION1":
				return dbHandler.projectFromSession1(attributes);
			case "EQUIPMENT2":
				return dbHandler.projectFromEquipment2(attributes);
			case "EQUIPMENT1":
				return dbHandler.projectFromEquipment1(attributes);
			case "AREA":
				return dbHandler.projectFromArea(attributes);
			case "FLOOR":
				return dbHandler.projectFromFloor(attributes);
			case "MEMBER":
				return dbHandler.projectFromMember(attributes);
			case "CLEARANCE":
				return dbHandler.projectFromClearance(attributes);
			default:
				return "Something went wrong";

		}
	}

	@Override
	public String showMembersOnDate(Date sessionDate) throws SQLException {
		Member3AttModel[] members = dbHandler.showMembersOnDate(sessionDate);
		String res = "";

		for (int i = 0; i < members.length; i++) {
			Member3AttModel member = members[i];
			String name = "Name: " + member.getName();
			String phone = "Phone: " + member.getPhone();
			String email = "Email: " + member.getEmail();
			res += name  + "\n" + phone  + "\n" + email + "\n\n";
		}
		return res;
	}

	@Override
	public String getEquipmentAndTheirQuantities() throws SQLException {
		System.out.println("getEquipmentAndTheirQuantities");
		Equipment3AttModel[] ans = dbHandler.getEquipmentAndTheirQuantities();
		String res = "";
		for (int i = 0; i< ans.length;i++) {
			Equipment3AttModel equipment = ans[i];
//			String name = "Name: " + equipment.getName();
			String condition = "Condition: " + equipment.getCondition();
			String count = "Count: " + equipment.getCount();
			res += condition + "\n" + count + "\n\n";
		}
		return res;
	}

	@Override
	public String getMembersWithMoreThanOneSessionAndTheirSessionInfo() throws SQLException {
		System.out.println("getMembersWithMoreThanOneSessionAndTheirSessionInfo");
		MemberAttendsModel[] ans = dbHandler.getMembersWithMoreThanOneSessionAndTheirSessionInfo();
		String res = "";
		for(int i = 0; i<ans.length; i++) {
			MemberAttendsModel model = ans[i];
			String MID = "Member ID: " + model.getMID();
//			String sessionDate = "Session Date: " + model.getSessionDate();
//			String startTime = "Start Time" + model.getStartTime();
			String count = "Count: " + model.getCount();
			res += MID + "\n" + count + "\n\n";
		}
		return res;
	}

	@Override
	public String getNumberOfLongSessionsMembersWithMoreThanOneLongSessionsHaveHad() throws SQLException {
		System.out.println("getNumberOfLongSessionsMembersWithMoreThanTwoLongSessionsHaveHad");
		MemberLongCountModel[] ans = dbHandler.getNumberOfLongSessionsMembersWithMoreThanOneLongSessionsHaveHad();
		String res = "";
		for(int i = 0; i<ans.length; i++) {
			MemberLongCountModel model = ans[i];
			String MID = "Member ID: " + model.getMID();
			String count = "Count: " + model.getCount();
			res += MID+ "\n" + count+ "\n\n";
		}
		return res;
	}

	@Override
	public String getStaffWhoHaveWorkedOnAllFloors() throws SQLException {
		System.out.println("getStaffWhoHaveWorkedOnAllFloors");
		StaffIDModel[] ans = dbHandler.getStaffWhoHaveWorkedOnAllFloors();
		String res = "";
		for(int i = 0; i<ans.length; i++) {
			StaffIDModel model = ans[i];
			String SID = "Staff ID: " + model.getSID();
			String name = "Name: " + model.getName();
			res += SID+ "\n" + name+ "\n\n";
		}
		return res;
	}

	@Override
	public String showAllData() throws SQLException {
		LinkedHashMap<String, List<String>> allTables = new LinkedHashMap<>();
		allTables.put(GymConstants.CLEARANCE_TABLE, List.of("CLEARANCELEVEL"));
		allTables.put(GymConstants.AREA_TABLE,Arrays.asList("ANUMBER","ATYPE","FNUMBER"));
		allTables.put(GymConstants.EQUIPMENT1_TABLE,Arrays.asList("NAME","USAGES"));
		allTables.put(GymConstants.EQUIPMENT2_TABLE,Arrays.asList("EID","NAME","CONDITION","ANUMBER","ATYPE"));
		allTables.put(GymConstants.SESSION1_TABLE,Arrays.asList("STARTTIME","ENDTIME","DURATION"));
		allTables.put(GymConstants.SESSION2_TABLE,Arrays.asList("SESSIONDATE","STARTTIME","ENDTIME"));
		allTables.put(GymConstants.FITNESS_CLASS1_TABLE,Arrays.asList("STARTTIME","ENDTIME","DURATION"));
		allTables.put(GymConstants.FITNESS_CLASS2_TABLE,Arrays.asList("FCTYPE","SESSIONDATE","STARTTIME","ENDTIME"));
		allTables.put(GymConstants.STAFF1_TABLE,Arrays.asList("STYPE","WORKINGHOURS"));
		allTables.put(GymConstants.STAFF2_TABLE,Arrays.asList("SID","NAME","STYPE"));
		allTables.put(GymConstants.PERSONAL_TRAINER1_TABLE,Arrays.asList("STYPE","WORKINGHOURS"));
		allTables.put(GymConstants.PERSONAL_TRAINER2_TABLE,Arrays.asList("SID","NAME","STYPE","PROGRAM"));
		allTables.put(GymConstants.ATTENDS_TABLE,Arrays.asList("MID","SESSIONDATE","STARTTIME","LOCKER"));
		allTables.put(GymConstants.OCCURS_IN_TABLE,Arrays.asList("SESSIONDATE","STARTTIME","ANUMBER","ATYPE"));
		allTables.put(GymConstants.UTILIZES_TABLE,Arrays.asList("EID","SESSIONDATE","STARTTIME"));
		allTables.put(GymConstants.LEADS_TABLE,Arrays.asList("SID","SESSIONDATE","STARTTIME"));
		allTables.put(GymConstants.WORKS_ON_TABLE,Arrays.asList("SID","FNUMBER"));
		allTables.put(GymConstants.TRAINS_TABLE,Arrays.asList("MID","SID"));
		allTables.put(GymConstants.USES_TABLE,Arrays.asList("MID","EID"));
		allTables.put(GymConstants.REQUIRES_TABLE,Arrays.asList("FNUMBER","CLEARANCELEVEL"));
		allTables.put(GymConstants.FLOOR_TABLE,Arrays.asList("FNUMBER","CAPACITY"));
		allTables.put(GymConstants.MEMBER_TABLE,Arrays.asList("MID","NAME","PHONE","EMAIL","CLEARANCELEVEL"));


		StringBuilder builder = new StringBuilder();
		for (String tableName: allTables.keySet()) {
			builder.append(projectFrom((String[]) allTables.get(tableName).toArray(),tableName)).append("\n\n\n");
		}
		return builder.toString();
	}

	/**
	 * TermainalTransactionsDelegate Implementation
	 * 
	 * Displays information about varies gym branches.
	 */
	public String showMembersWith(int clearanceLevel) throws SQLException {
		MemberModel[] members = dbHandler.getMembersWith(clearanceLevel);
		String res = "";

		for (int i = 0; i < members.length; i++) {
			MemberModel member = members[i];
			String MID = "MID: " + member.getMID();
			String name = "Name: " + member.getName();
			String phone = "Phone: " + member.getPhone();
			String email = "Email: " + member.getEmail();
			String clearance = "Clearance Level: " + member.getClearanceLevel();
			res += MID + "\n" + name  + "\n" + phone  + "\n" + email + "\n" + clearance + "\n\n";
		}
		return res;

		// MemberModel member = members[i];
		// 	System.out.println("MID: " + member.getMID());
		// 	System.out.println("Name: " + member.getName());
		// 	System.out.println("Phone: " + member.getPhone());
		// 	System.out.println("Email: " + member.getEmail());
		// 	S
		//
		// for (int i = 0; i < models.length; i++) {
		// GymModel model = models[i];
		//
		// // simplified output formatting; truncation may occur
		// System.out.printf("%-10.10s", model.getId());
		// System.out.printf("%-20.20s", model.getName());
		// if (model.getAddress() == null) {
		// System.out.printf("%-20.20s", " ");
		// } else {
		// System.out.printf("%-20.20s", model.getAddress());
		// }
		// System.out.printf("%-15.15s", model.getCity());
		// if (model.getPhoneNumber() == 0) {
		// System.out.printf("%-15.15s", " ");
		// } else {
		// System.out.printf("%-15.15s", model.getPhoneNumber());
		// }
		//
		// System.out.println();
		// }
	}

	/**
	 * TerminalTransactionsDelegate Implementation
	 * 
     * The TerminalTransaction instance tells us that it is done with what it's 
     * doing so we are cleaning up the connection since it's no longer needed.
     */ 
    public void transactionsFinished() {
    	dbHandler.close();
    	dbHandler = null;
    	
    	System.exit(0);
    }
    
    /**
	 * GUITransactionsDelegate Implementation
	 * 
	 * The GUITransactions instance tells us that the user is fine with dropping all
	 * existing tables
	 * and resetting to the default state for the user to use
	 */
	public void databaseSetup() {
		dbHandler.databaseSetup();

	}

	/**
	 * Main method called at launch time
	 */
	public static void main(String args[]) {
		Gym gym = new Gym();
		gym.start();
	}


//			switch (tableName) {
//		case "REQUIRES":
//			return dbHandler.projectFromRequires(attributes);
////				String resRequire = "";
////				for (int i = 0; i < ansRequires.length; i++) {
////
////					if (attributeSet.contains(""))
////					String level = "Level: " + ansRequires[i].getLevel();
////					String fnNumber = "FN Number: " + ansRequires[i].getFNumber();
////					resRequire += level + "\n" + fnNumber + "\n";
////				}
////				return resRequire;
//		case "USES":
//			UsesModel[] ansUses = dbHandler.projectFromUses(attributes);
//			String resUses = "";
//			for (int i = 0; i < ansUses.length; i++) {
//				String MID = "MID: " + ansUses[i].getMID();
//				String EID = "EID: " + ansUses[i].getEID();
//				resUses+= MID + "\n" + EID + "\n";
//			}
//			return resUses;
//		case "TRAINS":
//			TrainsModel[] ansTrains = dbHandler.projectFromTrains(attributes);
//			String resTrains = "";
//			for (int i = 0; i < ansTrains.length; i++) {
//				String MID = "MID: " + ansTrains[i].getMID();
//				String SID = "SID: " + ansTrains[i].getSID();
//				resTrains += MID + "\n" + SID + "\n";
//			}
//			return resTrains;
//		case "WORKSON":
//			WorksOnModel[] ansWorkOn = dbHandler.projectFromWorksOn(attributes);
//			String resWorkOn = "";
//			for (int i = 0; i < ansWorkOn.length; i++) {
//				String SID = "SID: " + ansWorkOn[i].getSID();
//				String fnNumber = "FN Number: " + ansWorkOn[i].getFNumber();
//				resWorkOn += SID + "\n" + fnNumber + "\n";
//			}
//			return resWorkOn;
//		case "LEADS":
//			LeadsModel[] ansLeads = dbHandler.projectFromLeads(attributes);
//			String resLeads = "";
//			for (int i = 0; i < ansLeads.length; i++) {
//				String SID = "SID: " + ansLeads[i].getSID();
//				String sessionDate= "Session Date: " + ansLeads[i].getSessionDate();
//				String startTime = "Start Time: " + ansLeads[i].getStartTime();
//				resLeads += SID + "\n" + sessionDate + "\n" + startTime + "\n";
//			}
//			return resLeads;
//		case "UTILIZES":
//			UtilizesModel[]ansUti = dbHandler.projectFromUtilizes(attributes);
//			String resUti = "";
//			for (int i=0; i< ansUti.length; i++) {
//				String EID = "EID: " + ansUti[i].getEID();
//				String sessionDate = "Session Date: " + ansUti[i].getSessionDate();
//				String sessionStart = "Session Start Time: " + ansUti[i].getSessionStartTime();
//				resUti += EID + "\n" + sessionDate + "\n" + sessionStart + "\n";
//			}
//			return resUti;
//		case "OCCURSIN":
//			OccursInModel[] ansOcc = dbHandler.projectFromOccursIn(attributes);
//			String resOcc = "";
//			for (int i =0; i < ansOcc.length; i++) {
//				String ANumber = "Area Number: " + ansOcc[i].getANumber();
//				String sessionDate = "Session Date: " + ansOcc[i].getSessionDate();
//				String sessionStart = "Session Start Time: " + ansOcc[i].getStartTime();
//				String Atype = "Area Type: " + ansOcc[i].getAType();
//				resOcc +=ANumber + "\n" + sessionDate + "\n" + sessionStart + "\n" +  Atype + "\n";
//			}
//			return resOcc;
//		case "ATTENDS":
//			AttendsModel[] ansAtt = dbHandler.projectFromAttends(attributes);
//			String resAtt = "";
//			for (int i = 0; i < ansAtt.length; i++) {
//				String MID= "MID: " + ansAtt[i].getMID();
//				String sessionDate = "Session Date: " + ansAtt[i].getSessionDate();
//				String startTime = "Start Time: " + ansAtt[i].getStartTime();
//				String locker = "Locker: " + ansAtt[i].getLocker();
//				resAtt += MID + "\n" + sessionDate + "\n" + startTime + "\n" + locker + "\n";
//			}
//			return resAtt;
//		case "PERSONALTRAINER2":
//			PersonalTrainer2Model[] ansPT2 = dbHandler.projectFromPersonalTrainer2(attributes);
//			String resPT2 = "";
//			for (int i = 0; i < ansPT2.length; i++) {
//				String SID = "SID: " + ansPT2[i].getSID();
//				String name = "Name: " + ansPT2[i].getName();
//				String SType = "Staff Type: " + ansPT2[i].getSType();
//				String program = "Program: " + ansPT2[i].getProgram();
//				resPT2 +=  SID+ "\n" + name+ "\n" + SType+ "\n" + program+ "\n";
//			}
//			return resPT2;
//		case "PERSONALTRAINER1":
//			PersonalTrainer1Model[] ansPT1 = dbHandler.projectFromPersonalTrainer1(attributes);
//			String resPT1= "";
//			for (int i = 0; i < ansPT1.length; i++) {
//				String SType = "Staff Type: " + ansPT1[i].getSType();
//				String workingHours = "Working Hours: " + ansPT1[i].getWorkingHours();
//				resPT1 += SType + "\n" + workingHours + "\n";
//			}
//			return resPT1;
//		case "STAFF2":
//			Staff2Model[] ansS2 = dbHandler.projectFromStaff2(attributes);
//			String resS2 = "";
//			for (int i = 0; i < ansS2.length; i++) {
//				String SID = "SID: " + ansS2[i].getSID();
//				String SType = "Staff Type: " + ansS2[i].getSType();
//				String name = "Name: " + ansS2[i].getName();
//				resS2 += SID + "\n" + SType + "\n"  +name + "\n";
//			}
//			return resS2;
//		case "STAFF1":
//			Staff1Model[] ansS1 = dbHandler.projectFromStaff1(attributes);
//			String resS1 = "";
//			for (int i = 0; i < ansS1.length; i++) {
//				String SType = "Staff Type: " + ansS1[i].getSType();
//				String workingHours = "Working Hours: " + ansS1[i].getWorkingHours();
//				resS1 += SType + "\n" + workingHours + "\n";
//			}
//			return resS1;
//		case "FITNESSCLASS2":
//			FitnessClass2Model[] ansFC2 = dbHandler.projectFromFitnessClass2(attributes);
//			String resFC2 = "";
//			for (int i = 0; i < ansFC2.length; i++) {
//				String FCType = "Fitness Class Type: " + ansFC2[i].getFCType();
//				String startTime = "Start Time: " + ansFC2[i].getStartTime();
//				String endTime = "End Time: " + ansFC2[i].getEndTime();
//				String date = "Date: " + ansFC2[i].getDate();
//				resFC2 += FCType + "\n" + startTime + "\n" + endTime + "\n" + date + "\n";
//			}
//			return resFC2;
//		case "FITNESSCLASS1":
//			FitnessClass1Model[] ansFC1 = dbHandler.projectFromFitnessClass1(attributes);
//			String resFC1 = "";
//			for (int i = 0; i < ansFC1.length; i++) {
//				String duration = "Duration: " + ansFC1[i].getDuration();
//				String startTime = "Start Time: " + ansFC1[i].getStartTime();
//				String endTime = "End Time: " + ansFC1[i].getEndTime();
//				resFC1 += duration + "\n" + startTime + "\n" + endTime + "\n";
//			}
//			return resFC1;
//		case "SESSION2":
//			Session2Model[] ansSession2 = dbHandler.projectFromSession2(attributes);
//			String resSession2 = "";
//			for (int i = 0; i < ansSession2.length; i++) {
//				String date = "Date: " + ansSession2[i].getDate();
//				String startTime = "Start Time: " + ansSession2[i].getStartTime();
//				String endTime = "End Time: " + ansSession2[i].getEndTime();
//				resSession2 += date + "\n" + startTime + "\n" + endTime + "\n";
//			}
//			return resSession2;
//		case "SESSION1":
//			Session1Model[] ansSession1 = dbHandler.projectFromSession1(attributes);
//			String resSession1 = "";
//			for (int i = 0; i < ansSession1.length; i++) {
//				String duration = "Date: " + ansSession1[i].getDuration();
//				String startTime = "Start Time: " + ansSession1[i].getStartTime();
//				String endTime = "End Time: " + ansSession1[i].getEndTime();
//				resSession1 += duration + "\n" + startTime + "\n" + endTime + "\n";
//			}
//			return resSession1;
//		case "EQUIPMENT2":
//			Equipment2Model[] ansEq2 = dbHandler.projectFromEquipment2(attributes);
//			String resEq2 = "";
//			for (int i = 0; i < ansEq2.length; i++) {
//				String EID = "Equipment ID: " + ansEq2[i].getEID();
//				String name = "Name: " + ansEq2[i].getName();
//				String condition = "Condition: " + ansEq2[i].getCondition();
//				String AType = "Area Type: " + ansEq2[i].getAType();
//				String ANumber = "Area Number: " + ansEq2[i].getANumber();
//				resEq2 += EID + "\n" + name + "\n" + condition + "\n" + AType  + "\n" +  ANumber + "\n";
//			}
//			return resEq2;
//		case "EQUIPMENT1":
//			Equipment1Model[] ansEq1 = dbHandler.projectFromEquipment1(attributes);
//			String resEq1 = "";
//			for (int i = 0; i < ansEq1.length; i++) {
//				String name = "Name: " + ansEq1[i].getName();
//				String usages = "Usages: " + ansEq1[i].getUsages();
//				resEq1 += name + "\n" + usages + "\n";
//			}
//			return resEq1;
//		case "AREA":
//			AreaModel[] ansArea =  dbHandler.projectFromArea(attributes);
//			String resArea = "";
//			for (int i = 0; i < ansArea.length; i++) {
//				String ANumber = "Area Number: " + ansArea[i].getANumber();
//				String AType = "Area Type: " + ansArea[i].getAType();
//				String FNumber = "Floor Number" + ansArea[i].getFNumber();
//				resArea += ANumber + "\n" + AType + "\n" + FNumber + "\n";
//			}
//			return resArea;
//		case "FLOOR":
//			FloorModel[] ansFloor = dbHandler.projectFromFloor(attributes);
//			String resFloor = "";
//			for (int i = 0; i < ansFloor.length; i++) {
//				String capacity = "Capacity: " + ansFloor[i].getCapacity();
//				String FNumber = "Floor Number" + ansFloor[i].getFNumber();
//				resFloor += capacity + "\n" + FNumber + "\n";
//			}
//			return resFloor;
//		case "MEMBER":
//			MemberModel[] ansMember = dbHandler.projectFromMember(attributes);
//			String resMember= "";
//			for (int i = 0; i < ansMember.length; i++) {
//				String MID = "MID: " + ansMember[i].getMID();
//				String name = "Name: " + ansMember[i].getName();
//				String phone = "Phone: " + ansMember[i].getPhone();
//				String email = "Email: " + ansMember[i].getEmail();
//				String clearance = "Clearance Level" + ansMember[i].getClearanceLevel();
//				resMember += MID + "\n" + name + "\n"+phone + "\n" + email + "\n" + clearance;
//			}
//			return resMember;
//		case "CLEARANCE":
//			ClearanceModel[] ansClearance = dbHandler.projectFromClearance(attributes);
//			String resClearance = "";
//			for (int i = 0; i < ansClearance.length; i++) {
//				String level = "Clearance Level: " + ansClearance[i].getLevel();
//				resClearance += level + "\n";
//			}
//			return resClearance;
//		default:
//			return "Something went wrong";
//
//	}
}

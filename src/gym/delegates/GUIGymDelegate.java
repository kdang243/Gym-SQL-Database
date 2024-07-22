package src.gym.delegates;

import src.gym.model.Member.MemberModel;

import java.sql.Date;
import java.sql.SQLException;

/**
 * This interface uses the delegation design pattern where instead of having
 * the GUITransactions class try to do everything, it will only
 * focus on handling the UI. The actual logic/operation will be delegated to the 
 * controller class (in this case Gym).
 * 
 * GUITransactions calls the methods that we have listed below but
 * Gym is the actual class that will implement the methods.
 *
 * Template taken from https://github.students.cs.ubc.ca/CPSC304/CPSC304_Java_Project
 */
public interface GUIGymDelegate {
	public void databaseSetup();
	public void deleteStaff(String SType) throws SQLException;

	public void insertMember(MemberModel model) throws SQLException;

	public String showMembersWith(int clearanceLevel) throws SQLException;

	public void updateMember(int MID, String phone, String email) throws SQLException;

	public String projectFrom(String[] attributes, String tableName) throws SQLException;

	public String showMembersOnDate(Date sessionDate) throws SQLException;

	public String getEquipmentAndTheirQuantities() throws SQLException;

	public String getMembersWithMoreThanOneSessionAndTheirSessionInfo() throws SQLException;

	public String getNumberOfLongSessionsMembersWithMoreThanOneLongSessionsHaveHad() throws SQLException;

	public String getStaffWhoHaveWorkedOnAllFloors() throws SQLException;

	public String showAllData() throws SQLException;
	public void transactionsFinished();
}

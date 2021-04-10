package com.orient.workflow.bean;

import java.io.Serializable;

public class AssignUser implements Serializable {

    /**
     * @Fields currentUser : 当前执行的用户或者用户组
     */
    private String currentUser;

    /**
     * @Fields currentGroup : 当前执行的角色或者角色组
     */
    private String currentGroup;

    /**
     * @Fields candidateUsers : 候选的用户或者用户组
     */
    private String candidateUsers;
    /**
     * @Fields candidateGroups : 候选的角色或者角色组
     */
    private String candidateGroups;

    public String getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    public String getCurrentGroup() {
        return currentGroup;
    }

    public void setCurrentGroup(String currentGroup) {
        this.currentGroup = currentGroup;
    }

    public String getCandidateUsers() {
        return candidateUsers;
    }

    public void setCandidateUsers(String candidateUsers) {
        this.candidateUsers = candidateUsers;
    }

    public String getCandidateGroups() {
        return candidateGroups;
    }

    public void setCandidateGroups(String candidateGroups) {
        this.candidateGroups = candidateGroups;
    }

    public static final String DELIMITER = ",";

}

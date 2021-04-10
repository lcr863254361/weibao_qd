package com.orient.workflow.ext.identity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.jbpm.api.JbpmException;
import org.jbpm.api.identity.Group;
import org.jbpm.api.identity.User;
import org.jbpm.pvm.internal.env.BasicEnvironment;
import org.jbpm.pvm.internal.env.EnvironmentImpl;
import org.jbpm.pvm.internal.id.DbidGenerator;
import org.jbpm.pvm.internal.identity.spi.IdentitySession;
 

/**
 * @ClassName EdmIdentitySessionImpl
 * 把流程中定义人员角色管理与当前系统的用户角色管理统一
 * @author zhulc@cssrc.com.cn
 * @date 2012-6-11
 */

@SuppressWarnings("unchecked")
public class EdmIdentitySessionImpl implements IdentitySession {

  protected Session session;

  public EdmIdentitySessionImpl() {
    this.session = BasicEnvironment.getFromCurrent(Session.class);
  }


  public String createUser(String userName, String password, String familName,String businessEmail) {
    long dbid = EnvironmentImpl.getFromCurrent(DbidGenerator.class).getNextId();
    UserImpl user = new UserImpl(String.valueOf(dbid),userName, password, familName, businessEmail);
    session.save(user);
    return user.getId();
  }

  public UserImpl findUserById(String userName) {
    return (UserImpl) session.createCriteria(UserImpl.class).add(Restrictions.eq("userName", userName)).uniqueResult();

  }

  public List<User> findUsersById(String... userName) {
    List<User> users = session.createCriteria(UserImpl.class).add(Restrictions.in("userName", userName)).list();
    if (userName.length != users.size()) {
      throw new JbpmException("not all users were found: " + Arrays.toString(userName));
    }
    return users;
  }
  
  public List<User> findUsersByDBId(String... userIds) {
    List<User> users = session.createCriteria(UserImpl.class).add(Restrictions.in("db_id", userIds)).list();
    if (userIds.length != users.size()) {
      throw new JbpmException("not all users were found: " + Arrays.toString(userIds));
    }
    return users;
  }

  public List<User> findUsers() {
    return session.createCriteria(UserImpl.class).list();
  }

  public void deleteUser(String userId) {
    // lookup the user
    UserImpl user = findUserById(userId);
    if(user == null){
    	throw new JbpmException("user not found: " + userId);
    }
    // cascade the deletion to the memberships
    List<GroupMemberImpl> memberships = session.createCriteria(GroupMemberImpl.class).add(Restrictions.eq("id.userId", user.getDb_id())).list();
    // delete the related memberships
    for (GroupMemberImpl membership : memberships) {
      session.delete(membership);
    }
    // delete the user
    session.delete(user);
  }

  public String createGroup(String groupName, String groupType, String parentGroupId) {
    GroupImpl group = new GroupImpl();
    long dbid = EnvironmentImpl.getFromCurrent(DbidGenerator.class).getNextId();
    group.setDb_id(new BigDecimal(dbid));
    group.setName(groupName);
    session.save(group);
    return group.getId();
  }

  public List<User> findUsersByGroup(String groupId) {
    List<GroupMemberImpl> memberships = session.createCriteria(GroupMemberImpl.class).add(Restrictions.eq("id.roleId", groupId)).list();
    String[] userIds = new String[memberships.size()];
    int i = 0;
    for (GroupMemberImpl membership : memberships) {
      userIds[i] = membership.getId().getUserId();
    }
    return findUsersByDBId(userIds);
  }

  public GroupImpl findGroupById(String groupName) {
    return (GroupImpl) session.createCriteria(GroupImpl.class).add(Restrictions.eq("name",groupName)).uniqueResult();
  }

  public List<Group> findGroupByDBId(String... groupIds) {
    BigDecimal[] groupId = new BigDecimal[groupIds.length];
    for(int i=0;i<groupIds.length;i++){
      groupId[i] = new BigDecimal(groupIds[i]);
    }
    List<Group> groups = session.createCriteria(GroupImpl.class).add(Restrictions.in("db_id", groupId)).list();
    if (groupIds.length != groups.size()) {
      throw new JbpmException("not all users were found: " + Arrays.toString(groupIds));
    }
    return groups;
  }
  
  public List<Group> findGroupsByUserAndGroupType(String userId, String groupType) {
    return session.createQuery(
            "select distinct m.group" + " from " + GroupMemberImpl.class.getName() + " as m where m.user.id = :userId" + " and m.group.type = :groupType")
            .setString("userId", userId).setString("groupType", groupType).list();
  }

  public List<Group> findGroupsByUser(String userId) {
    UserImpl user = findUserById(userId);
    if(user == null){
    	throw new JbpmException("user not found: " + userId);
    }
    List<GroupMemberImpl> memberships = session.createCriteria(GroupMemberImpl.class).add(Restrictions.eq("id.userId", user.getDb_id())).list();
    if(memberships == null || memberships.size()==0)return new ArrayList<Group>();
    String[] groupsIds = new String[memberships.size()];
    int i = 0;
    for (GroupMemberImpl membership : memberships) {
      groupsIds[i] = membership.getId().getRoleId();
      i++;
    }
    return findGroupByDBId(groupsIds);

  }

  public List<Group> findGroups() {
    return session.createCriteria(GroupImpl.class).list();
  }

  public void deleteGroup(String groupId) {
    // look up the group
    GroupImpl group = findGroupById(groupId);
    // cascade the deletion to the memberships
    List<GroupMemberImpl> memberships = session.createCriteria(GroupMemberImpl.class).add(Restrictions.eq("id.roleId", group.getDb_id().toString())).list();
    // delete the related memberships
    for (GroupMemberImpl membership : memberships) {
      session.delete(membership);
    }
    // delete the group
    session.delete(group);
  }

  public void createMembership(String userName, String groupId, String role) {
    UserImpl user = findUserById(userName);
    if (user == null) {
      throw new JbpmException("user " + userName + " doesn't exist");
    }
    GroupImpl group = findGroupById(groupId);
    if (group == null) {
      throw new JbpmException("group " + groupId + " doesn't exist");
    }
    GroupMemberImpl membership = new GroupMemberImpl();
    GroupMemberImplId id = new GroupMemberImplId();
    id.setUserId(user.getDb_id());
    id.setRoleId(group.getDb_id().toString());
    membership.setId(id);
    session.save(membership);
  }

  public void deleteMembership(String userName, String groupId, String role) {
    UserImpl user = findUserById(userName);
    GroupImpl group = findGroupById(groupId);
    GroupMemberImpl membership = (GroupMemberImpl) session.createCriteria(GroupMemberImpl.class).add(
            Restrictions.eq("id.userId", user.getDb_id())).add(Restrictions.eq("id.roleId", group.getDb_id().toString())).uniqueResult();
    session.delete(membership);
  }
}

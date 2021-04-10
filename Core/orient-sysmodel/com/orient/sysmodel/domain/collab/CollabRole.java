package com.orient.sysmodel.domain.collab;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.orient.utils.UtilFactory;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * collab role
 *
 * @author Seraph
 *         2016-07-08 上午10:48
 */
@Entity
@Table(name = "COLLAB_ROLE")
public class CollabRole implements Serializable {

    @Entity
    @Table(name = "CWM_SYS_USER")
    static public class User implements Serializable {

        public User() {

        }

        public User(String id, String allName, String userName, String depId, String state) {
            this.id = id;
            this.allName = allName;
            this.userName = userName;
            this.depId = depId;
            this.state = state;
        }

        private String id;
        private String allName;
        private String userName;
        private String depId;
        private String state;

        @Id
        @Column(name = "ID", nullable = false, insertable = true, updatable = true, precision = -127)
        @GeneratedValue(generator = "sequence")
        @GenericGenerator(name = "sequence", strategy = "sequence", parameters = {@org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_CWM_SYS_USER")})
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        @Basic
        @Column(name = "ALL_NAME")
        public String getAllName() {
            return allName;
        }

        public void setAllName(String allName) {
            this.allName = allName;
        }

        @Basic
        @Column(name = "USER_NAME")
        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        @Basic
        @Column(name = "DEP_ID")
        public String getDepId() {
            return depId;
        }

        public void setDepId(String depId) {
            this.depId = depId;
        }

        @Basic
        @Column(name = "STATE")
        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }

            if (!(obj instanceof User)) {
                return false;
            }

            User user = (User) obj;
            if (!this.id.equals(user.id)) {
                return false;
            }

            if (!(this.allName == null ? user.allName == null : this.allName.equals(user.allName))) {
                return false;
            }

            return true;
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder(17, 31).append(this.id).append(allName).toHashCode();
        }

        private static final long serialVersionUID = 1L;
    }

    public static final String MODEL_NAME = "modelName";
    public static final String NODE_ID = "nodeId";
    public static final String NAME = "name";


    private Long id;
    private String name;
    private String nodeId;

    transient private Set<CollabFunction> functions = new HashSet<>();
    transient private Set<User> users = new HashSet<>();
    private List<Long> functionIds = UtilFactory.newArrayList();
    private Set<String> userIds = new HashSet<>();

    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true, precision = -127)
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence", parameters = {@org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_COLLAB_ROLE")})
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "NODE_ID")
    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "COLLAB_ROLE_FUNCTION", joinColumns = @JoinColumn(name = "ROLE_ID"), inverseJoinColumns = @JoinColumn(name = "FUNCTION_ID"))
    public Set<CollabFunction> getFunctions() {
        return functions;
    }

    public void setFunctions(Set<CollabFunction> functions) {
        this.functions = functions;
    }

    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "COLLAB_ROLE_USER", joinColumns = @JoinColumn(name = "ROLE_ID"), inverseJoinColumns = @JoinColumn(name = "USER_ID"))
    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Transient
    public Set<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(Set<String> userIds) {
        this.userIds = userIds;
    }

    @Transient
    public List<Long> getFunctionIds() {
        return functionIds;
    }

    public void setFunctionIds(List<Long> functionIds) {
        this.functionIds = functionIds;
    }

    private static final long serialVersionUID = 1L;
}

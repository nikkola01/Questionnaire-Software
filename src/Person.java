/**
 * @author Nikola Nikolov
 * @version 5 May 2021
 */
public class Person {
    private Role role;
    private String username;

    public Person() {
        this(Role.OTHER,"");
    }

    public Person(Role role, String username) {
        this.role = role;
        this.username = username;
    }

    /**
     * Get the role of the person
     * @return the role of the person
     */
    public Role getRole() {
        return role;
    }

    /**
     * This method sets the value for role
     *
     * @param role becomes the value
     */
    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * Get the username of the person
     * @return the username of the person
     */
    public String getUsername() {
        return username;
    }

    /**
     * This method sets the value for username
     *
     * @param username becomes the value
     */
    public void setUsername(String username) {
        this.username = username;
    }
}

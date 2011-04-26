import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Matchmaker {

    /**
     * @param args
     */
    public static void main(String[] args) {
        String [] members = {"BETTY F M A A C C",
                             "TOM M F A D C A",
                             "SUE F M D D D D",
                             "ELLEN F M A A C A",
                             "JOE M F A A C A",
                             "ED M F A D D A",
                             "SALLY F M C D A B",
                             "MARGE F M A A C C"};
        String curMember = "BETTY";
        int sf = 2;
        
        Matchmaker match = new Matchmaker();
        String[] matches = match.getBestMatches(members, curMember, sf);
        for (int i=0; i < matches.length; i++) {
            System.out.println(matches[i]);
        }
        
        curMember = "JOE";
        sf = 1;
        
        matches = match.getBestMatches(members, curMember, sf);
        for (int i=0; i < matches.length; i++) {
            System.out.println(matches[i]);
        }
        
        curMember = "MARGE";
        sf = 4;
        
        matches = match.getBestMatches(members, curMember, sf);
        for (int i=0; i < matches.length; i++) {
            System.out.println(matches[i]);
        }
        System.exit(0);

    }
    
    /**
     * 
     * @param members - members contains information about all the members.  
     * Elements of members are
     * of the form "NAME G D X X X X X X X X X X" 
     *  - NAME represents the member's name 
     *  - G represents the gender of the current user. [MF]
     *  - D represents the requested gender of the potential mate. [MF]
     *  - Each X indicates the member's answer to one of the multiple-choice
     *  - questions.  The first X is the answer to the first question, the second is the
     *  - answer to the second question, et cetera. [Capital letters each]
     *  
     *  Members will have between 1 and 50 elements
     *  Each element of members will be between 7 and 44 length
     *  Name will have length between 1 and 20 inclusive, and contain
     *     uppercase letters A-Z
     *     
     *     Xs will be one cap letter each. There will be between 1 and 10 of them
     *     
     *  - No two elements will have the same NAME.
     *    Names are case sensitive.
     *     
     * 
     * @param currentUser is the name of the user who made the "Get Best Mates" request. 
     *    consists of between 1 and 20, inclusive, uppercase letters, A-Z,
     *    and must be a member.
     * @param sf is an integer representing the similarity factor.
     *    value between 1 and 10, inclusive. Must be <= number of Xs of members.
     * @return a String[] consisting of members' names who have at least sf
     *     identical answers to currentUser and are of the requested gender.  
     *     The names should be returned in order from most identical answers 
     *     to least.  If two members have the same number of identical answers
     *     as the currentUser, the names should be returned in the same 
     *     relative order they were inputted.
     */
    public String[] getBestMatches(String[] members, String currentUser, int sf) {
        MatchMember ok = null;
        ArrayList<MatchMember> okUsers = new ArrayList<MatchMember>();
        String testAns[] = null;
        int matchSf = 0;
        String curUserRec[] = getCurrentUserRec(members, currentUser).split(" ");
        String curUserAns[] = new String[curUserRec.length-3];
        for (int x=3; x < curUserRec.length; x++) {
            curUserAns[x-3] = curUserRec[x];
        }
        
        for (int i=0; i < members.length; i++) {
            String [] curMatch = members[i].split(" ");
            if (!curMatch[0].equals(curUserRec[0]) && curMatch[1].equals(curUserRec[2])) { 
                testAns = new String[curMatch.length-3];
                for (int y=3; y < curMatch.length; y++) {
                    testAns[y-3] = curMatch[y];
                }
                matchSf = getNumMatches(curUserAns, testAns);
                if (matchSf >= sf) {
                    ok = new MatchMember(curMatch[0], matchSf);
                    okUsers.add(ok);
                }
            }
        }
        
        String[] result = sortOkUsers(okUsers);
        
        return result;
    }
    
    private int getNumMatches(String[] memAns, String[] testAns) {
        int numMatch = 0;
        for (int i=0; i < memAns.length; i++) {
            if (memAns[i].equals(testAns[i])) numMatch++;
        }
        
        return numMatch;
    }
    
    private String getCurrentUserRec(String[] members, String curUser) {
        String userRec = null;
        Pattern p = Pattern.compile(curUser + "*");
        Matcher m = null;
        
        for (int i=0; i < members.length; i++) {
            m = p.matcher(members[i]);
            if (m.find()) {
                userRec = members[i];
            }
        }
        
        return userRec;
    }
    
    private String[] sortOkUsers(ArrayList<MatchMember> okUsers) {
        String[] sortedUsers = new String[okUsers.size()];
        Collections.sort(okUsers, new Comparator<MatchMember>() {
            public int compare(MatchMember o1, MatchMember o2) {
                return o2.sf - o1.sf;
            }
        });
        for (int i=0; i < okUsers.size(); i++) {
            sortedUsers[i] = okUsers.get(i).memName;
        }
        return sortedUsers;
    }
    
    private class MatchMember {
        public String memName = null;
        public int sf;
        
        public MatchMember(String name, int sf) {
            this.memName = name;
            this.sf = sf;
        }
    }

}

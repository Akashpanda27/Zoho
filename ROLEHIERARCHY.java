import java.util.*;
public class ROLEHIERARCHY {
    static class Node{
        String val;
        Node parent;
        List<Node> list;
        public Node(String val,Node parent){
            this.val=val;
            this.parent=parent;
            list=new ArrayList<>();
        }
    }
    static Scanner sc=new Scanner(System.in);
    public static void main(String[] args) {
        
        String operations[]=new String[]{"Exit","Add Sub Role.","Display Roles","Delete Role.","Add User.","Display Users.","Display Users and Sub Users.","Delete User.","Number of users from top.","Height of role hierachy.","Common boss of users"};
        Map<String,Node> map=new HashMap<>();
        Map<String,String> users=new HashMap<>();
        Map<String,List<String>> roles=new HashMap<>();
        for(int i=0;i<operations.length;i++){
            System.out.println(i+". "+operations[i]);
        }
        System.out.println("Enter root role name");
        String name=sc.next();
        Node root=new Node(name,null);
        map.put(name,root);
        System.out.println(root.val);
        
        System.out.println("Operation to be performed");
        
        while(true){
            int n=sc.nextInt();
            if(n==0) break;
            switch(n){
                case 1: addSubRole(map);
                    break;
                case 2: displayRoles(root);
                    break;
                case 3: deleteRole(map,users,roles);
                    break;
                case 4: addUsers(users,roles);
                    break;
                case 5: displayUsers(users);
                    break;
                case 6: displayusersAndSubusers(root,roles);
                    break;
                case 7: deleteUser(users,roles);
                    break;
                case 8: NumberOfUsersFromTop(map,roles,users);
                    break;
                case 9: heightOfTree(root);
                    break;    
                case 10: commonBoss(root,roles,users,map);
                    break;
                default: break;
            }
        }
        
        
        
    }

    public static void addSubRole(Map<String,Node> map){
        System.out.println("Enter sub role name");
        String c=sc.next();
        System.out.println("Enter reporting to role name");
        String p=sc.next();
        Node par=map.get(p);
        Node child=new Node(c,par);
        par.list.add(child);
        map.put(c,child);
        
    }

    public static void displayRoles(Node root){
        Queue<Node> q=new LinkedList<>();
        q.add(root);
        while(!q.isEmpty()){
            int size=q.size();
            while(size--!=0){
                Node curr=q.poll();
                System.out.print(curr.val+",");
                for(Node temp: curr.list){
                    q.add(temp);
                }
            }
        }
    }
    
    public static void deleteRole(Map<String,Node> map,Map<String,String> users,Map<String,List<String>> roles){
        System.out.println("Enter the role to be deleted");
        String s=sc.next();
        System.out.println("Enter the role to be transferred");
        String t=sc.next();
        Node to=map.getOrDefault(t,new Node(t,null));
        Node from=map.get(s);
        for(Node e: from.list){
            e.parent=to;
        }
        to.list.addAll(from.list);
        map.remove(s);
        Node par=from.parent;
        par.list.remove(from);
        roles.putIfAbsent(s, new ArrayList<>());
        
        for(String username: roles.get(s)){
            users.put(username,t);
            System.out.println("hi "+username);
        }
        
    }
    public static void addUsers(Map<String,String> users,Map<String,List<String>> roles){
        System.out.println("Enter User Name");
        String name=sc.next();
        System.out.println("Enter Role");
        String role=sc.next();
        roles.putIfAbsent(role,new ArrayList<>());
        roles.get(role).add(name);
        users.put(name,role);
    }

    public static void displayUsers(Map<String,String> users){
        for(String e: users.keySet()){
            System.out.println(e+"-"+users.get(e));
        }
    }

    public static void displayusersAndSubusers(Node root,Map<String,List<String>> roles){
        Queue<Node> q=new LinkedList<>();
        q.add(root);
        while(!q.isEmpty()){
            int size=q.size();
            while(size--!=0){
                Node currRole=q.poll();
                if(roles.containsKey(currRole.val)){
                    List<String> usernames=getNames(currRole,roles);
                    for(String e: roles.get(currRole.val)){
                        System.out.print(e+"-");
                        for(String names: usernames){
                            System.out.print(names+",");
                        }
                        System.out.println();
                    }
                }
                for(Node temp: currRole.list){
                    q.add(temp);
                }
            }
        }
    }
    public static List<String> getNames(Node root,Map<String,List<String>> roles){
        List<String> res=new ArrayList<>();
        Queue<Node> q=new LinkedList<>();
        q.add(root);
        while(!q.isEmpty()){
            int size=q.size();
            while(size--!=0){
                Node curr=q.poll();
                if(curr!=root){
                    roles.putIfAbsent(curr.val, new ArrayList<>());
                    res.addAll(roles.get(curr.val));
                }
                for(Node temp: curr.list){
                    q.add(temp);
                }
            }
        }
        return res;

    }

    public static void deleteUser(Map<String,String> users,Map<String,List<String>> roles){
        System.out.println("Enter username to be deleted :");
        String name=sc.next();
        String currRole=users.get(name);
        users.remove(new String(name));
        roles.get(currRole).remove(new String(name));
    }
    public static void NumberOfUsersFromTop(Map<String,Node>map,Map<String,List<String>> roles,Map<String,String> users){
        System.out.println("Enter user name :");
        String s=sc.next();
        String role=users.get(s);
        Node curr=map.get(role);
        int count=0;
        while(curr.parent!=null){
            List<String> usernames=roles.getOrDefault(curr.parent.val,new ArrayList<>());
            count+=usernames.size();
            curr=curr.parent;
        }
        System.out.println("Number of users from top "+count);
    }

    public static void heightOfTree(Node root){
        int h=0;
        Queue<Node> q=new LinkedList<>();
        q.add(root);
        while(!q.isEmpty()){
            h++;
            int size=q.size();
            while(size--!=0){
                Node curr=q.poll();
                for(Node temp: curr.list){
                    q.add(temp);
                }
            }
        }
        System.out.println(h);
    }

    public static void commonBoss(Node root,Map<String,List<String>> roles,Map<String,String> users,Map<String,Node> map){
        System.out.println("Enter user 1");
        String name1=sc.next();
        System.out.println("Enter user 2");
        String name2=sc.next();
        Set<String> set=new HashSet<>();
        Node user1=map.get(users.get(name1));
        Node user2=map.get(users.get(name2));
        while(user1!=null){
            set.add(user1.val);
            user1=user1.parent;
        }
        String res="";
        while(user2!=null){
            if(set.contains(user2.val)){
                res=user2.val;
            }
            user2=user2.parent;
        }
        List<String> list=roles.get(res);
        System.out.println("Top most common boss :"+list.get(0));
    }
    

    
}
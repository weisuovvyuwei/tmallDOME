package tmall.bean;

public class User {			//用户类
	private String name;	//用户名
	private String password;//用户密码
	private int id;			//id
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAnonymousName(){
		if (null == name) {
			return null;
		}
		if (name.length()<=1) {
			return "*";
		}
		if (name.length()<=2) {
			return name.substring(0,1)+"*";
		}
		char[] cs = name.toCharArray();
		for(int i=1;i<cs.length-1;i++){
			cs[i]='*';
		}
		return new String(cs);
	}
	public static void main(String[] args) {
		String string = "爱ss";
		User user=new User();
		user.setName(string);
		System.out.println(user.getAnonymousName());
	}
}

	

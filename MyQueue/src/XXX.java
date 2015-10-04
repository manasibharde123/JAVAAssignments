
public class  XXX	{
  int oI = 1;

  XXX aXXX = new XXX();

  public XXX() {
  }
  public XXX(XXX aXXX)	{
	this.aXXX = aXXX;
  }
  public void m1(int i){
	oI = i;
  }
  public void m2(int i){
	oI = i;
	m1(12);
	aXXX.m1(24);
	System.out.println("om: " + this);
	System.out.println("	oI " + oI);
	System.out.println("aXXX.om: " + aXXX);
	System.out.println("	aXXX.oI " + aXXX.oI);
  }

  public static void main(String args[] )	{
	XXX aaXXX = new XXX(new XXX() );

        aaXXX.m2(3);
  }
}
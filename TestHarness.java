class TestHarness
{
    public static void main (String[] args)
        {
        BluePrint obj1 = new BluePrint("Areaf", 19);
        System.out.println("Hey " + obj1.getterForName() + ", you are " + obj1.getterForAge() +
        " years old");
        // New object
        BluePrint obj2 = new BluePrint();
        obj2.setterForName("Also Areaf");
        obj2.setterForAge(19);
        System.out.println("Hey again " + obj2.getterForName());
        }
}
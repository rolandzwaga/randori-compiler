package demo.foo
{
import demo.foo.support.AnotherStaticClass;
import demo.foo.support.Bar;
import demo.foo.support.IA;
import demo.foo.support.IB;
import demo.foo.support.IC;
import demo.foo.support.Mode1;
import demo.foo.support.ParticleAssets;
import demo.foo.support.Static2;
import demo.foo.support.StaticClass4;
import demo.foo.support.StaticClass5;
import demo.foo.support.StaticClass6;
import demo.foo.support.StaticClass7;
import demo.foo.support.StaticClass8;
import demo.foo.support.SupportClassA;
import demo.foo.support.globalFunc;
import demo.foo.support.trace;

public class Dependencies
{
    
    public function runtime_dependencies():void {
        var o:IC = null;
        instance(o);
        instance(IA);
        instance(new ParticleAssets.ParticleCoffeeXML());
        var b:Object = (true) ? StaticClass8 : false;
        var obj:Object = {};
        var b:Boolean = (obj instanceof StaticClass7);
        var classes:Array = [StaticClass6];
        foo(StaticClass5.FOO);
        
        var a:* = (true) ? globalFunc : null;
        var c:Dependencies = new Dependencies();
        var myClass = Mode1;
        var x = new Bar();
        trace("Yo");
        AnotherStaticClass.current = 3;
        
        SupportClassA.inputMode = Static2.property;

    }
    
    public function instance1(value:IB):IB
    {
    }
    
    private function instance(value:Object):void
    {
    }
    
    private function foo(value:String):void
    {
    }
    
    public static var classes:Array = ["SomeClass", StaticClass4];
    public static var pfoo:Dependencies = Dependencies;
    public static var pfoo:String = ClassB.FOO;
    public static var pbar:Object = MyFunction();
    public static var pbar2 = MyTestFunction();
    
}
}
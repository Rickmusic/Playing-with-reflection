import java.lang.reflect.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
public class Inspector
{
	Object obj = null;
	Class<?> objClass = null;
	boolean recursive = false;
	LinkedList<Field> fields  = new LinkedList<Field>(); 
	public void inspect(Object obj, boolean recursive) throws IllegalArgumentException, IllegalAccessException 
	{
		this.obj = obj;
		this.recursive = recursive;
		objClass = obj.getClass();
		
		System.out.println("Name = " + getDeclaringClass());
		System.out.println("SuperClass = " + getSuper());
		
		// Theses methods print out everything and return what's included// 
		// for the purposes of this program I will just be calling them to print the output // 
		getInterfaces();
		getMethods();
		getConstructors();
		System.out.println("Fields:");
		getFields(objClass, obj);
	}
	
	
	public Method[] getMethods()
	{
		printMethods(objClass.getMethods());
		return objClass.getMethods();	
	}

	private LinkedList getFields(Class<?> objClass, Object obj) throws IllegalArgumentException, IllegalAccessException
	{
		
		Field[] subFields;
		subFields = objClass.getDeclaredFields();
		for (int x =0; x < subFields.length; x++)
		{	
			subFields[x].setAccessible(true);
			fields.add(subFields[x]);
			System.out.print("	" + subFields[x].getName());
			System.out.print(" with type " + subFields[x].getType() );
			System.out.print(" modifiers " + Modifier.toString(subFields[x].getModifiers()));
			if(subFields[x].getType().isPrimitive())
			{
				System.out.print(" with value " + subFields[x].get(obj));
			}
			else
			{
				Object cur = subFields[x].get(obj);
				if (cur == null) System.out.print(" with null value");
				else if(cur instanceof Iterable || cur.getClass().isArray())
				{
					try
					{
						@SuppressWarnings("rawtypes")
						List list = Arrays.asList(cur); 
						for (Object y : list)
						{
							System.out.print(" with Value of " + y + " ");
						}
					}
					catch (Exception e)
					{
						System.out.println("");
						
					}
				}
				else 
				{
					if (recursive == false)
					{
						System.out.print(" with value " + cur);
					}
					else
					{
						System.out.print("\n<Containing>\n");
						getFields(cur.getClass(), cur);
						System.out.print("<Containing\\>\n");
					}
					
				}
			}
			System.out.println();
			
		}
		if (objClass.getSuperclass()!= null)getFields(objClass.getSuperclass(), obj );
		return fields;
	}

/*	private void fieldsInvolved(Field field)
	{
		boolean iterable = false;
		System.out.println();
		Class<?>[] interfaces = field.getClass().getInterfaces();
		

	}*/


	public Constructor[] getConstructors()
	{
		Constructor[] constructors = objClass.getConstructors();
		for(int x = 0; x < constructors.length; x++ )
		{
			System.out.println("Constructor " + x + " : ");
			Parameter[] parameters = constructors[x].getParameters();
			if (parameters.length >= 1)
			{
				System.out.print("	Parameters: ");
				for (int y = 0; y < parameters.length; y++)
				{
					System.out.print(parameters[y].getType());
					if (y != parameters.length - 1) System.out.print(", ");
					System.out.println();
				}
			}
			else System.out.println("	No Parameters");
			System.out.println("	Modifiers: " + Modifier.toString(constructors[x].getModifiers()));
		}
		return constructors;
		
	}

	public String getDeclaringClass()
	{
		return obj.getClass().getName();
	}
	public String getSuper()
	{
		return 	objClass.getSuperclass().getName();
	}
	
	public Class<?>[] getInterfaces()
	{
		Class<?>[] interfaces = objClass.getInterfaces();
		if(interfaces.length >0)
		{
			System.out.print("Interfaces = ");
			for (int x = 0; x < interfaces.length; x++)
			{
				System.out.print(interfaces[x].getName());
				if(x != interfaces.length) System.out.print(", ");
			}
			System.out.println();
		}
		else System.out.println("No Interfaces");
		
		return interfaces;
	}
	
	private void printMethods(Method[] methods)
	{
		for (Method x : methods)
		{
			System.out.println("Method " + x.getName());
			System.out.println("	With Exceptions: " +  x.getExceptionTypes().getClass().getName());
			printParameters(x);
			System.out.println("	Return Type: " + x.getReturnType());
			System.out.println("	Modifiers: " + Modifier.toString(x.getModifiers()));
		}
	}
	
	private void printParameters(Method x)
	{
		Parameter[] parameters = x.getParameters();
			if (parameters.length >= 1)
			{
				System.out.print("	Parameters: ");
				for (int y = 0; y < parameters.length; y++)
				{
					System.out.print(parameters[y].getType());
					if (y != parameters.length - 1) System.out.print(", ");
				}
				System.out.println();
			}
			else System.out.println("	No Parameters");
			
		
	}
}


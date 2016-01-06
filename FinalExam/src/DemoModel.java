
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class DemoModel {

	private Class<?> loadedClass;
	private Object instance;
	
	public DemoModel(String className) throws ModelException {
		try {
			loadedClass = Class.forName(className);
			instance = loadedClass.newInstance();
		} 
		catch(Exception e) {
			throw new ModelException(e);
		}
	}

	public Class<?> getLoadedClass() {
		return loadedClass;
	}

	public Object getInstance() {
		return instance;
	}

	public void setProperties(List<Property> properties) throws ModelException{
		try {
			for(Property prop : properties ) {
				String setName = "set" + prop.getName();
				Method setMethod = loadedClass.getMethod(setName, new Class<?>[] { prop.getType() });
				if(setMethod != null) {
					// need a big if/else to determine how o convert the base value
					// from a string to a specific type, e.g. an int.
				}
			}
		}
		catch(Exception e) {
			throw new ModelException(e);
		}
	}
	
	public List<Property> getProperties() throws ModelException {
		try {
			List<Property> names = new ArrayList<>();
			
			Method[] methods = loadedClass.getMethods();
			
			for(Method method : methods) {
				String methodName = method.getName();
				if(methodName.startsWith("set") &&
					method.getParameterCount() == 1 &&
					method.getReturnType() == Void.TYPE) {					
					String propertyName = methodName.substring(3);
					String getName = "get" + propertyName;
					Method getMethod = loadedClass.getMethod(getName, new Class<?>[0]);
					if(getMethod != null) {
						if(getMethod.getReturnType() == method.getParameterTypes()[0]) {
							Property property = new Property();
							property.setName(propertyName);
							property.setType(getMethod.getReturnType());
							Object result = getMethod.invoke(instance, new Object[0]);
							property.setValue(result);
							names.add(property);
						}
					}
				}
			}
			return names;
		}
		catch(Exception e ) {
			throw new ModelException(e);
		}
	}
}
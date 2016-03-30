package handlers;

import org.apache.axis2.AxisFault;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.description.AxisDescription;
import org.apache.axis2.description.AxisModule;
import org.apache.axis2.modules.Module;
import org.apache.neethi.Assertion;
import org.apache.neethi.Policy;

public class MyModule implements Module{

	@Override
	public void applyPolicy(Policy arg0, AxisDescription arg1) throws AxisFault {
	}

	@Override
	public boolean canSupportAssertion(Assertion arg0) {
		return false;
	}

	@Override
	public void engageNotify(AxisDescription arg0) throws AxisFault {		
	}

	@Override
	public void init(ConfigurationContext arg0, AxisModule arg1) throws AxisFault {
		System.out.println("Inicio de Log Module");
		
	}

	@Override
	public void shutdown(ConfigurationContext arg0) throws AxisFault {
		System.out.println("Apago Log Module");
	}

}

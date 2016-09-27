package br.com.fdp.pedidos.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.fdp.pedidos.model.Package;
import br.com.fdp.pedidos.repository.PackageRepository;



@Component
public class PackageConverter implements Converter{
	
	@Autowired
	private PackageRepository embrulhoRepository;

	
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value.isEmpty())
			return null;
		try {
			Package embrulho = embrulhoRepository.findOne(new Long(value));
			return embrulho;
		} catch (Exception e) {
			return null;
		}
	}

	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value instanceof Package) {
			Package embrulho = (Package) value;
			return embrulho.getId().toString();
		} else {
			return null;
		}
	}

}

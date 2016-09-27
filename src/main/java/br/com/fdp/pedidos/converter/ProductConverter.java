package br.com.fdp.pedidos.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.fdp.pedidos.model.Product;
import br.com.fdp.pedidos.repository.ProductRepository;



@Component
public class ProductConverter implements Converter{
	
	@Autowired
	private ProductRepository productRepository;

	
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value.isEmpty())
			return null;
		try {
			Product product = productRepository.findOne(new Long(value));
			return product;
		} catch (Exception e) {
			return null;
		}
	}

	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value instanceof Product) {
			Product product = (Product) value;
			return product.getId().toString();
		} else {
			return null;
		}
	}

}

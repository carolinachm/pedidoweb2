package br.com.fdp.pedidos.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.fdp.pedidos.model.Payment;
import br.com.fdp.pedidos.repository.PaymentRepository;



@Component
public class PaymentConverter implements Converter{
	
	@Autowired
	private PaymentRepository paymentRepository;

	
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value.isEmpty())
			return null;
		try {
			Payment payment = paymentRepository.findOne(new Long(value));
			return payment;
		} catch (Exception e) {
			return null;
		}
	}

	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value instanceof Payment) {
			Payment payment = (Payment) value;
			return payment.getId().toString();
		} else {
			return null;
		}
	}

}

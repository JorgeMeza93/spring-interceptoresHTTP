package com.cursodespring.springboot.interceptor.app.interceptores;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component("horario")
public class HorarioInterceptor implements HandlerInterceptor {
	@Value("${config.horario.apertura}")
	private Integer apertura;
	@Value("${config.horario.cierre}")
	private Integer cierre;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Calendar calendario = Calendar.getInstance();
		int horaActual = calendario.get(Calendar.HOUR_OF_DAY);
		if( horaActual >= apertura && horaActual< cierre){
			StringBuilder mensaje = new StringBuilder("Bienvenido al servicio de atención a clientes");
			mensaje.append(", atendemos desde las ");
			mensaje.append(apertura);
			mensaje.append("hrs. Hasta las ");
			mensaje.append(cierre);
			mensaje.append("hrs. Gracias por su visita"); 
			request.setAttribute("mensaje", mensaje.toString());
			return true;
		}
		response.sendRedirect(request.getContextPath().concat("/cerrado"));
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		String mensaje = (String)request.getAttribute("mensaje");
		if(modelAndView != null){
			modelAndView.addObject("horario", mensaje);
		}
	}
	
}

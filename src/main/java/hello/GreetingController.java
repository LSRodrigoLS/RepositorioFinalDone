package hello;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.EmpleadoDAO;
import model.Empleado;
import model.Respuesta;

@RestController
public class GreetingController {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@RequestMapping("/greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new Greeting(counter.incrementAndGet(), String.format(template, name));
	}

	@RequestMapping(value = "/empleado/listar", method = RequestMethod.GET)
	public List<Empleado> listado(@RequestParam(value = "name", defaultValue = "World") String name) {
		EmpleadoDAO dao = new EmpleadoDAO();
		return dao.listarEmpleados();
	}

	@RequestMapping(value = "/empleado/registrar", method = RequestMethod.POST)
	public Respuesta registrar(@RequestParam(value = "empleado", defaultValue = "null") String emp) {
		Respuesta res = new Respuesta();
		try {
			ObjectMapper mapper = new ObjectMapper();
			Empleado empleado = mapper.readValue(emp, Empleado.class);
			int resultado = new EmpleadoDAO().registrar(empleado);
			System.out.println("Este es el resultado esperado : " + resultado);
			if (resultado == 1) {
				res.setId(1);
				res.setResultado("Sé registró correcto");
			} else {
				res.setId(1);
				res.setResultado("Sé registró mal");
			}
		} catch (IOException e) {
			res.setId(0);
			res.setResultado("Generate Exception");
			e.printStackTrace();
		}
		return res;
	}
	
	@RequestMapping(value = "/empleado/actualizar", method = RequestMethod.POST)
	public Respuesta actualizar(@RequestParam(value = "empleado", defaultValue = "null") String emp) {
		Respuesta res = new Respuesta();
		try {
			ObjectMapper mapper = new ObjectMapper();
			Empleado empleado = mapper.readValue(emp, Empleado.class);
			int resultado = new EmpleadoDAO().actualizar(empleado);
			System.out.println("Este es el resultado esperado : " + resultado);
			if (resultado == 1) {
				res.setId(1);
				res.setResultado("Sé actualizó");
			} else {
				res.setId(1);
				res.setResultado("No se actualizo");
			}
		} catch (IOException e) {
			res.setId(0);
			res.setResultado("Generate Exception");
			e.printStackTrace();
		}
		return res;
	}

	@RequestMapping(value = "/empleado/eliminar", method = RequestMethod.POST)
	public Respuesta eliminar(@RequestParam(value = "codigo", defaultValue = "null") String codigo) {
		int cod = Integer.parseInt(codigo);
		int resultado = new EmpleadoDAO().eliminar(cod);
		Respuesta res = new Respuesta();
		if (resultado == 1) {
			res.setId(1);
			res.setResultado("éxito al eliminar");
			return res;
		} else {
			res.setId(2);
			res.setResultado("fracaso al eliminar");
			return res;
		}
	}

}

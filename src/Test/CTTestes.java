package Test;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.*;
import org.junit.runners.MethodSorters;

import Model.*;
import DAL.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CTTestes {
	private Usuario us;
	private Evento ev;
	private UsuarioDAL usDal;
	private EventoDAL evDal;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		us = new Usuario();
		ev = new Evento();
		usDal = new UsuarioDAL();
		evDal = new EventoDAL();
		ev.setData(LocalDate.of(2099, 12, 31));
		ev.setNome("Evento de Teste");
		ev.setQuantiaMax(1);
		us.setNome("Nome de Teste");
		us.setCpf("999.999.999-99");
		us.setEmail("Teste@EventosSenac.com");
	}

	@After
	public void tearDown() throws Exception {
	}
	// feito em apenas um metodo para garantir precisão no teste ja que possui foreign key
	@Test
	public void test1() {
		assertNotNull(ev = evDal.insert(ev));
		if (ev != null) {
			// checks
			assertTrue(usDal.checkCpf(us));
			assertTrue(usDal.checkDate(ev.getId()));
			assertTrue(usDal.checkEmail(us));
			assertTrue(usDal.checkTotal(ev.getId()));
			assertFalse(evDal.checkNegative(ev.getQuantiaMax()));//just for the sake of having it here / added later cuz I forgot during the creation/planning

			// rest
			us.setEvento_id(ev.getId());
			assertNotNull(us = usDal.insert(us));
			if (us != null) {
				assertFalse(usDal.checkTotal(ev.getId()));// should be false now that event is full
				us.setNome("Nome de Teste Novo");
				assertTrue(usDal.update(us));
				assertNotNull(usDal.get(us.getId()));
				assertNotNull(usDal.getAll());
				assertEquals(1, evDal.totalNow(ev.getId()));// should return 1 before deletion
				assertTrue(usDal.delete(us.getId()));// "participantes" should go back to 0
			}
			ev.setNome("Evento de Teste Updated");
			assertTrue(evDal.update(ev));
			assertEquals(0, evDal.totalNow(ev.getId()));
			assertNotNull(evDal.get(ev.getId()));
			assertNotNull(evDal.getAll());
			assertTrue(evDal.delete(ev.getId()));
		}
	}
}
import org.junit.Test;
import org.mockito.Mockito;
import ru.netology.patient.entity.BloodPressure;
import ru.netology.patient.entity.HealthInfo;
import ru.netology.patient.entity.PatientInfo;
import ru.netology.patient.repository.PatientInfoRepository;
import ru.netology.patient.service.alert.SendAlertService;
import ru.netology.patient.service.medical.MedicalServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;

public class MedicalServiceTest {
    private final PatientInfoRepository patientInfoRepository;
    private final SendAlertService sendAlertService;
    private final MedicalServiceImpl medicalServiceImpl;
    private final PatientInfo PATIENT_INFO = new PatientInfo(
            "Иван",
            "Петров",
            LocalDate.of(1980, 11, 26),
            new HealthInfo(
                    new BigDecimal("36.65"),
                    new BloodPressure(120, 80)
            )
    );
    private String patientId;
    private final String MESSAGE = String.format("Warning, patient with id: %s, need help", PATIENT_INFO.getId());

    public MedicalServiceTest() {
        patientInfoRepository = Mockito.mock(PatientInfoRepository.class);
        Mockito.when(patientInfoRepository.add(PATIENT_INFO))
                .thenReturn(PATIENT_INFO.getId());
        Mockito.when(patientInfoRepository.getById(patientInfoRepository.add(PATIENT_INFO)))
                .thenReturn(PATIENT_INFO);
        patientId = patientInfoRepository.add(PATIENT_INFO);

        sendAlertService = Mockito.mock(SendAlertService.class);
        medicalServiceImpl = new MedicalServiceImpl(patientInfoRepository, sendAlertService);
    }

    @Test
    public void test_send_message_while_temperature_checking() {
        BigDecimal currentTemperature = new BigDecimal("34.20");
        medicalServiceImpl.checkTemperature(patientId, currentTemperature);
        Mockito.verify(sendAlertService, Mockito.atLeastOnce()).send(MESSAGE);
    }

    @Test
    public void test_send_message_while_blood_checking() {
        BloodPressure currentPressure = new BloodPressure(60, 120);
        medicalServiceImpl.checkBloodPressure(patientId, currentPressure);
        Mockito.verify(sendAlertService, Mockito.atLeastOnce()).send(MESSAGE);
    }

    @Test
    public void test_unsend_message_while_normal_temp() {
        BigDecimal currentTemperature = new BigDecimal("36.65");
        medicalServiceImpl.checkTemperature(patientId, currentTemperature);
        Mockito.verify(sendAlertService, Mockito.never()).send(MESSAGE);
    }

    @Test
    public void test_unsend_message_while_normal_pressure() {
        BloodPressure currentPressure = new BloodPressure(120, 80);
        medicalServiceImpl.checkBloodPressure(patientId, currentPressure);
        Mockito.verify(sendAlertService, Mockito.never()).send(MESSAGE);
    }
}
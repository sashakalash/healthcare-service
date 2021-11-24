import org.junit.Test;
import org.junit.jupiter.api.Assertions;
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
                    new BigDecimal("39.65"),
                    new BloodPressure(120, 80)
            )
    );

    public MedicalServiceTest() {
        patientInfoRepository = Mockito.mock(PatientInfoRepository.class);
        Mockito.when(patientInfoRepository.add(PATIENT_INFO))
                .thenReturn(PATIENT_INFO.getId());
        Mockito.when(patientInfoRepository.getById(patientInfoRepository.add(PATIENT_INFO)))
                .thenReturn(PATIENT_INFO);

        sendAlertService = Mockito.mock(SendAlertService.class);
        medicalServiceImpl = new MedicalServiceImpl(patientInfoRepository, sendAlertService);
    }

    @Test
    public void test_send_message_while_temperature_checking() {
        PatientInfo patientInfo = PATIENT_INFO;
        String message = String.format("Warning, patient with id: %s, need help", patientInfo.getId());System.out.printf("Warning, patient with id: %s, need help", patientInfo.getId());
        String patientId = patientInfoRepository.add(PATIENT_INFO);
        BigDecimal currentTemperature = new BigDecimal("37.9");
        medicalServiceImpl.checkTemperature(patientId, currentTemperature);
        Mockito.verify(sendAlertService, Mockito.atLeastOnce()).send(message);
    }

    @Test
    public void test_send_message_while_blood_checking() {
        PatientInfo patientInfo = PATIENT_INFO;
        String message = String.format("Warning, patient with id: %s, need help", patientInfo.getId());
        String patientId = patientInfoRepository.add(PATIENT_INFO);
        BloodPressure currentPressure = new BloodPressure(60, 120);
        medicalServiceImpl.checkBloodPressure(patientId, currentPressure);
        Mockito.verify(sendAlertService, Mockito.atLeastOnce()).send(message);
    }

    @Test
    public void test_get_patient_info() {
        String patientId = patientInfoRepository.add(PATIENT_INFO);
        PatientInfo assertion = patientInfoRepository.getById(patientId);
        PatientInfo expected = PATIENT_INFO;
        Assertions.assertEquals(expected, assertion);
    }
}
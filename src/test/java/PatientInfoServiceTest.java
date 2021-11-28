import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;
import ru.netology.patient.entity.BloodPressure;
import ru.netology.patient.entity.HealthInfo;
import ru.netology.patient.entity.PatientInfo;
import ru.netology.patient.repository.PatientInfoRepository;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PatientInfoServiceTest {
    private final PatientInfoRepository patientInfoRepository;
    private final PatientInfo PATIENT_INFO = new PatientInfo(
            "Иван",
            "Петров",
            LocalDate.of(1980, 11, 26),
            new HealthInfo(
                    new BigDecimal("36.65"),
                    new BloodPressure(120, 80)
            )
    );

    public PatientInfoServiceTest() {
        patientInfoRepository = Mockito.mock(PatientInfoRepository.class);
        Mockito.when(patientInfoRepository.add(PATIENT_INFO))
                .thenReturn(PATIENT_INFO.getId());
        Mockito.when(patientInfoRepository.getById(patientInfoRepository.add(PATIENT_INFO)))
                .thenReturn(PATIENT_INFO);
    }

    @Test
    public void test_get_patient_info() {
        String patientId = patientInfoRepository.add(PATIENT_INFO);
        PatientInfo assertion = patientInfoRepository.getById(patientId);
        PatientInfo expected = PATIENT_INFO;
        Assertions.assertEquals(expected, assertion);
    }
}
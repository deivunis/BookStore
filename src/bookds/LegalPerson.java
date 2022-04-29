package bookds;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

@Getter
@Setter
@RequiredArgsConstructor
@Data
@Entity

public final class LegalPerson extends User implements Serializable {

    private String companyTitle;
    private String ceo;
    private String registrationNumber;
    private String vatNumber;


    public LegalPerson(String login, String password, String email, String address, Role role, String companyTitle, String ceo, String registrationNumber, String vatNumber) {
        super(login, password, email, address, role);
        this.companyTitle = companyTitle;
        this.ceo = ceo;
        this.registrationNumber = registrationNumber;
        this.vatNumber = vatNumber;
    }

    @Override
    public String toString() {
        return "Company{" +
                "companyTitle='" + companyTitle + '\'' +
                ", ceo='" + ceo + '\'' +
                ", registrationNumber='" + registrationNumber + '\'' +
                ", vatNumber='" + vatNumber + '\'' +
                '}';
    }
}

package com.TaleWeaver.FirstBackendPrototype.models;

import com.TaleWeaver.FirstBackendPrototype.models.enums.SessionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @ManyToOne
    private User user;

    @CreationTimestamp
    private Date creationDate;

    @Enumerated(EnumType.STRING)
    private SessionType sessionType;

    public boolean isExpired() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getCreationDate());
        calendar.add(Calendar.SECOND, getSessionType().getExpirationSeconds());
        Date expirationDate = calendar.getTime();
        return expirationDate.before(new Date());
    }
}

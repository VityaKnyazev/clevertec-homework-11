package ru.clevertec.ecl.knyazev.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "gift_certificate")
public class GiftCertificate {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 25)
	private String name;

	@Column(nullable = false, length = 50)
	private String description;

	@Column(nullable = false, precision = 9, scale = 3)
	private BigDecimal price;

	@Basic
	@Temporal(TemporalType.DATE)
	private Date duration;

	@Column(name = "create_date", nullable = false)
	private LocalDateTime createDate;

	@Column(name = "last_update_date")
	private LocalDateTime lastUpdate;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "gift_certificate_tag", joinColumns = @JoinColumn(name = "gift_certificate_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
	private List<Tag> tags;
}

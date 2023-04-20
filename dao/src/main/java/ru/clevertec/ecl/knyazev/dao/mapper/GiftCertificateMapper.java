package ru.clevertec.ecl.knyazev.dao.mapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ru.clevertec.ecl.knyazev.entity.GiftCertificate;

public class GiftCertificateMapper implements RowMapper<GiftCertificate> {

	@Override
	public GiftCertificate mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		GiftCertificate giftCertificate = GiftCertificate.builder()
				.id(rs.getLong("id"))
				.name(rs.getString("name"))
				.description(rs.getString("description"))
				.price(new BigDecimal(rs.getDouble("price")))
				.duration(rs.getDate("duration"))
				.createDate(rs.getTimestamp("create_date").toLocalDateTime())
				.lastUpdate(rs.getTimestamp("last_update_date").toLocalDateTime())
				.build();
		
		return giftCertificate;
	}
	

}

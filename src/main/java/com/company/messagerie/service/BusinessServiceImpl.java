package com.company.messagerie.service;

import java.util.List;
import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import com.company.messagerie.model.MessageRequest;
import com.company.messagerie.util.JsonUtil;
import com.datastax.driver.core.ConsistencyLevel;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.utils.UUIDs;

public class BusinessServiceImpl implements BusinessService{

    @Autowired
    private Session cassandraSession;
	
	@Override
	public boolean persistMessage(MessageRequest message) {
		
		if (message == null) {
			return false;
		}
		
		Insert insert = QueryBuilder.insertInto("messages");
        insert.setConsistencyLevel(ConsistencyLevel.QUORUM);
        insert.value("content", JsonUtil.fromObjectToJson(message));
        insert.value("recordId", UUIDs.timeBased());
        cassandraSession.execute(insert);
		return true;
	}

	@Override
	public List<MessageRequest> getAllMessages() {
        Statement accountQuery = QueryBuilder.select().from("messages");
        ResultSet results = cassandraSession.execute(accountQuery);
        Stream<Row> stream = StreamSupport.stream(Spliterators.spliteratorUnknownSize(results.iterator(), 
        		Spliterator.IMMUTABLE|Spliterator.NONNULL), false);
        
        return stream.filter(Objects::nonNull)
				.map(JsonUtil::fromRowToObject).collect(Collectors.toList());
	}
}

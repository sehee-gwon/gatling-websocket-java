package com.example.gatling.infrastructure.simulations;

import com.example.gatling.design.domain.DesignMetaData;
import com.example.gatling.design.domain.Sheet;
import com.example.gatling.design.domain.enumeration.ActionType;
import com.example.gatling.design.domain.enumeration.Target;
import com.example.gatling.design.presentation.DesignRequest;
import com.example.gatling.infrastructure.stomp.SendFrame;
import com.example.gatling.infrastructure.stomp.StompFrame;
import com.example.gatling.infrastructure.utils.DesignMetaDataMaker;
import com.example.gatling.infrastructure.utils.ParserUtils;
import com.example.gatling.infrastructure.utils.RandomUtils;
import com.example.gatling.infrastructure.utils.SheetXmlMaker;
import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.ws;

public class SheetInsertSimulation extends Simulation {
    static final String DESIGN_ID_NAME = "designId";

    HttpProtocolBuilder httpProtocol = http
            .baseUrl("http://localhost:8080")
            .acceptHeader("application/xhtml+xml;q=0.8,application/xml,*/*;q=0.6")
            .doNotTrackHeader("1")
            .acceptLanguageHeader("en-US,en;q=0.5")
            .acceptEncodingHeader("gzip, deflate")
            .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")
            .wsBaseUrl("ws://localhost:8080");

    List<Integer> designIds = Arrays.asList(1, 2, 3);
    List<Integer> sheetIds = new ArrayList<>();

    {
        for (int i=1; i<=10; i++) {
            sheetIds.add(i);
        }
    }

    int elementSize = 5;

    ChainBuilder insert =
            exec(ws("Connect WS").connect("/connect"))
            .pause(1)
            .foreach(designIds, DESIGN_ID_NAME).on(
                exec(ws("Insert Sheets").sendText(session -> {
                    String designIdx = SheetXmlMaker.DESIGN_ID_FORMAT + session.getInt(DESIGN_ID_NAME);
                    List<Sheet> sheets = SheetXmlMaker.createSheets(ActionType.INSERT, sheetIds, elementSize);

                    long teamIdx = RandomUtils.randNumber(100000, 200000);
                    long accountId = RandomUtils.randNumber(100000, 200000);
                    DesignMetaData designMetaData =
                            DesignMetaDataMaker.createDesignMetaData(session.getInt(DESIGN_ID_NAME), teamIdx, accountId, "User" + accountId, sheets);

                    DesignRequest request = DesignRequest.builder()
                            .designIdx(designIdx)
                            .target(Target.SHEET)
                            .actionType(ActionType.INSERT)
                            .sheets(sheets)
                            .designMetaData(designMetaData)
                            .build();

                    StompFrame frame = SendFrame.builder()
                            .body(ParserUtils.toJsonString(request))
                            .contentType(MediaType.APPLICATION_JSON)
                            .build();

                    return frame.createPayload();
                })).pause(1)
            )
            .pause(1)
            .exec(ws("Close WS").close());

    ScenarioBuilder users = scenario("Users").exec(insert);

    {
        setUp(users.injectOpen(atOnceUsers(1))).protocols(httpProtocol);
    }
}
package com.example.gatling.infrastructure.utils.tags;

import com.example.gatling.infrastructure.utils.RandomUtils;
import lombok.Getter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.UUID;

@Getter
public class TextTag extends SingleTag {
    private Element textData;
    private Element renderPos;
    private Element text;
    private Element font;
    private Element curve;
    private Element effect;

    public TextTag(Document doc, UUID elementId) {
        super(doc, "TEXT", elementId);
    }

    public void addTextData(Document doc) {
        this.textData = doc.createElement("TextData");
        this.textData.setTextContent("[{\"a\":1,\"s\":1.4,\"il\":0,\"o\":\"NONE\",\"l\":0,\"c\":[{\"t\":\"↛\",\"c\":\"#000000\",\"z\":48.5514,\"g\":\"#00000000\",\"f\":0,\"dx\":100,\"ds\":0,\"oa\":false,\"ol\":255,\"oc\":\"#000000\",\"oz\":5,\"sa\":false,\"sl\":40,\"sg\":135,\"sc\":\"#000000\",\"sd\":10,\"sp\":\"\",\"sz\":1,\"sr\":0,\"yb\":false,\"yf\":\"Noto Sans CJK KR Regular\",\"yd\":467,\"yi\":false,\"yu\":false,\"yt\":false,\"sst\":\"NONE\"}]}]");
        this.element.appendChild(this.textData);
    }

    public void addRenderPos(Document doc) {
        this.renderPos = doc.createElement("RenderPos");
        this.renderPos.setTextContent("{\"c\":[{\"x\":0,\"y\":53.82,\"w\":34.258,\"a\":56.32,\"d\":15.536,\"t\":\"↛\",\"s\":48.552,\"ds\":0,\"f\":\"rgb(0, 0, 0)\",\"yd\":467,\"r\":[0,-2.5,34.258,72.5]}],\"s\":[0,0,34.258,67.5],\"t\":[0,17.019,49.424,40.394]}");
        this.element.appendChild(this.renderPos);
    }

    public void addText(Document doc) {
        this.text = doc.createElement("Text");
        this.text.setTextContent(RandomUtils.randAlphabet());
        this.element.appendChild(this.text);
    }

    public void addFont(Document doc) {
        this.font = doc.createElement("Font");
        this.font.setAttribute("Color", RandomUtils.randColorCode());
        this.font.setAttribute("Family", "Noto Sans CJK KR Regular");

        Element style = doc.createElement("Style");
        style.setAttribute("Bold", String.valueOf(RandomUtils.randBoolean()));
        style.setAttribute("Italic", String.valueOf(RandomUtils.randBoolean()));
        style.setAttribute("Strikeout", String.valueOf(RandomUtils.randBoolean()));
        style.setAttribute("Underline", String.valueOf(RandomUtils.randBoolean()));

        this.font.appendChild(style);
        this.element.appendChild(this.font);
    }

    public void addCurve(Document doc) {
        this.curve = doc.createElement("Curve");
        this.curve.setAttribute("IsCurved", String.valueOf(RandomUtils.randBoolean()));
        this.element.appendChild(this.curve);
    }

    public void addEffect(Document doc) {
        this.effect = doc.createElement("Effect");
        this.effect.setAttribute("TextSpace", "0");
        this.effect.setAttribute("ScaleX", String.valueOf(RandomUtils.randNumber(0, 2)));

        Element outline = doc.createElement("Outline");
        outline.setAttribute("DoOutline", String.valueOf(RandomUtils.randBoolean()));
        outline.setAttribute("Color", "0000000");
        outline.setAttribute("Size", String.valueOf(RandomUtils.randNumber(1,6)));

        Element shadow = doc.createElement("Shadow");
        shadow.setAttribute("DoShadow", String.valueOf(RandomUtils.randBoolean()));
        shadow.setAttribute("Color", "28000000");
        shadow.setAttribute("Distance", String.valueOf(RandomUtils.randNumber(1, 11)));
        shadow.setAttribute("Angle", String.valueOf(RandomUtils.randNumber(300, 316)));
        shadow.setAttribute("Spread", "0");

        Element fill = doc.createElement("Fill");
        fill.setAttribute("Type", "COLOR");

        this.effect.appendChild(outline);
        this.effect.appendChild(shadow);
        this.effect.appendChild(fill);
        this.element.appendChild(this.effect);
    }
}

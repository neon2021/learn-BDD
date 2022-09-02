package unittest.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.ImmutableMap;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * TODO: function description
 *
 * @author neon2021 on 2022/9/2
 */
public class JSONManipulationsTest {
    static Map<String, String> realClassNameMapToAlias = ImmutableMap.of(
            "unittest.json.ParentV1", "parent_v1",
            "unittest.json.BoyV1", "boy_v1",
            "unittest.json.GirlV1", "girl_v1"
    );
    String JAVA_OBJECT_V1_FASTJSON_STR = "{\n" + //
            "    \"@type\": \"unittest.json.ParentV1\",\n" + //
            "    \"age\": 38,\n" + //
            "    \"children\":\n" + //
            "    [\n" + //
            "        {\n" + //
            "            \"@type\": \"unittest.json.BoyV1\",\n" + //
            "            \"age\": 0,\n" + //
            "            \"soccerTeamName\": \"Buckingham\"\n" + //
            "        },\n" + //
            "        {\n" + //
            "            \"@type\": \"unittest.json.GirlV1\",\n" + //
            "            \"age\": 0,\n" + //
            "            \"playSlidingPlate\": true\n" + //
            "        }\n" + //
            "    ],\n" + //
            "    \"hasWork\": false,\n" + //
            "    \"name\": \"caesar\"\n" + //
            "}"; //

    public static String traverseForJVM2DB(String rootName, JsonNode root) {

        if (root.isObject()) {
            Iterator<String> fieldNames = root.fieldNames();

            while (fieldNames.hasNext()) {
                String fieldName = fieldNames.next();
                JsonNode fieldValue = root.get(fieldName);
                String foundAlias = traverseForJVM2DB(fieldName, fieldValue);
                if (StringUtils.isNoneBlank(foundAlias)) {
                    System.out.println("object replaced: foundAlias= " + foundAlias);
                    ((ObjectNode) root).put(fieldName, foundAlias);
                }
            }
        } else if (root.isArray()) {
            ArrayNode arrayNode = (ArrayNode) root;
            for (int i = 0; i < arrayNode.size(); i++) {
                JsonNode arrayElement = arrayNode.get(i);
                String foundAlias = traverseForJVM2DB(null, arrayElement);
                System.out.println("omit array: foundAlias= " + foundAlias);
            }
        } else {
            // JsonNode root represents a single value field - do something with it.
            if (StringUtils.equalsIgnoreCase("@type", rootName)) {
                System.out.println("for @type value is : " + root);

                if (realClassNameMapToAlias.containsKey(root.textValue())) {
                    return realClassNameMapToAlias.get(root.textValue());
                }
            }
        }

        return null;
    }

    @Test
    public void test_convert() {
        BoyV1 childA = BoyV1.boyBuilder().age(5).name("Tony").soccerTeamName("Buckingham").build();
        GirlV1 childB = GirlV1.girlBuilder().age(3).name("Jame").isPlaySlidingPlate(true).build();
        List<ChildV1> children = new ArrayList<>();
        children.add(childA);
        children.add(childB);
        ParentV1 parent = ParentV1.builder().age(38).name("caesar").children(children).build();
        String fastjsonStrFromJavaObjectsV1 = JSON.toJSONString(parent, SerializerFeature.WriteClassName);
        System.out.println("fastjsonStrFromJavaObjectsV1:\n" + fastjsonStrFromJavaObjectsV1);
        System.out.println("expected:\n" + StringUtils.deleteWhitespace(JAVA_OBJECT_V1_FASTJSON_STR));
        Assertions.assertThat(fastjsonStrFromJavaObjectsV1).isEqualTo(StringUtils.deleteWhitespace(JAVA_OBJECT_V1_FASTJSON_STR));
//        Assertions.assertThat(fastjsonStrFromJavaObjectsV1).isEqualToIgnoringWhitespace(JAVA_OBJECT_V1_FASTJSON_STR); // why failed?

        // this solution cannot work, because we cannot change the global config on online servers.
//        JSON.setDefaultTypeKey("@@type");
//        JSONObject jsonObject = JSON.parseObject(fastjsonStrFromJavaObjectsV1);
//        System.out.println("jsonObject: \n" + jsonObject);
//        for (String key : jsonObject.keySet()) {
//            System.out.println("key=" + key + ", value=" + jsonObject.get(key) + ", type of value=" + jsonObject.get(key).getClass());
//        }

        try {
            // step1: jvm --> db
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNodeFromJavaObjectsV1 = objectMapper.readTree(fastjsonStrFromJavaObjectsV1);
            traverseForJVM2DB(null, jsonNodeFromJavaObjectsV1);

            System.out.printf("step1: jvm --> db   result:\n"+jsonNodeFromJavaObjectsV1);


            // step2: jvm <-- db
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class ParentV1 {

    int age;
    String name;
    boolean hasWork;
    List<ChildV1> children;

}

abstract class ChildV1 {
    int age;
    String name;

    public ChildV1() {
    }

    public ChildV1(int age, String name) {
        this.age = age;
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
class BoyV1 extends ChildV1 {

    String soccerTeamName;

    @Builder(builderMethodName = "boyBuilder")
    public BoyV1(int age, String name, String soccerTeamName) {
        super(age, name);
        this.soccerTeamName = soccerTeamName;
    }
}

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
class GirlV1 extends ChildV1 {

    boolean isPlaySlidingPlate;

    @Builder(builderMethodName = "girlBuilder")
    public GirlV1(int age, String name, boolean isPlaySlidingPlate) {
        super(age, name);
        this.isPlaySlidingPlate = isPlaySlidingPlate;
    }

}
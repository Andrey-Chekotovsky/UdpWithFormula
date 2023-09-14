package org.example.DTO;

import org.json.JSONObject;

public class FormulaArgsMapper {
    public FormulaArgsDto mapFromJson(JSONObject json)
    {
        FormulaArgsDto args = new FormulaArgsDto();
        args.setX(json.getInt("x"));
        args.setY(json.getInt("y"));
        args.setZ(json.getInt("z"));
        return args;
    }
}

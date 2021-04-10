package com.orient.weibao.bean.table;



//./app/javascript/lib/export/jquery.js
//./app/javascript/lib/export/FileSaver.js
//./app/javascript/lib/export/jquery.wordexport.js

/**
 * @author fangbin
 * 2020/3/23
 */
public class Html  {

  private String header=
          "    <script src=\"/app/javascript/lib/export/jquery.js\"></script>\n" +
          "    <script src=\"/app/javascript/lib/export/FileSaver.js\"></script>\n" +
          "    <script src=\"/app/javascript/lib/export/jquery.wordexport.js\"></script>";


    private Table tableBody;


    public void setTableBody(Table tableBody) {
        this.tableBody = tableBody;
    }

    public Table getTableBody() {
        return tableBody;
    }

    private String script = "\n" +
            "<script>\n" +
            "  $(\"#export\").click(function(){\n" +
            "  \t var title = $(\"#title\").text()\n" +
            "     $(\"#myDiv\").wordExport(title)\n" +
            "  })\n" +
            "</script>";

    @Override
    public String toString() {
        return header+tableBody.toString()+script;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }
}

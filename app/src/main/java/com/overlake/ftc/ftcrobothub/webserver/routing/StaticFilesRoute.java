package com.overlake.ftc.ftcrobothub.webserver.routing;

import android.content.Context;
import android.os.Environment;
import android.webkit.MimeTypeMap;

import com.overlake.ftc.ftcrobothub.R;
import com.overlake.ftc.ftcrobothub.webserver.routing.IRoute;
import com.overlake.ftc.ftcrobothub.webserver.routing.RouteHandler;
import com.overlake.ftc.ftcrobothub.webserver.routing.RoutingPath;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.NanoHTTPD.Response;
import fi.iki.elonen.NanoHTTPD.IHTTPSession;
import fi.iki.elonen.NanoHTTPD.Method;

public class StaticFilesRoute implements IRoute
{
    private static Map<String, String> filePaths = new HashMap<String, String>();
    private static String baseURI;

    private String filePath;
    private String root;

    public StaticFilesRoute(String baseURI, Context context)
    {
        this.baseURI = baseURI;
        root = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/" +
                context.getString(R.string.app_name) + "/";
        filePath = root + cleanBaseURI(baseURI);
    }

    private String cleanBaseURI(String baseURI) {
        if (baseURI.startsWith("/")) {
            return baseURI.substring(1);
        } else {
            return baseURI;
        }
    }

    @Override
    public void setRoutes(Map<RoutingPath, RouteHandler> routingTable, IRoute route)
    {
        setStaticRoutes(filePath, routingTable);
    }

    private void setStaticRoutes(String path, Map<RoutingPath, RouteHandler> routingTable) {
        File file = getFile(path);
        if (file.isFile()) {
            addHandlerForFile(file, routingTable);
        } else {
            for (File child : file.listFiles()) {
                setStaticRoutes(path + "/" + child.getName(), routingTable);
            }
        }
    }

    private File getFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    private void addHandlerForFile(File file, Map<RoutingPath, RouteHandler> routingTable) {
        filePaths.put(getURI(file), file.getAbsolutePath());
        RoutingPath path = new RoutingPath(getURI(file), Method.GET);
        routingTable.put(path, createHandler(file));
    }

    private String getURI(File file) {
        return "/" + file.getAbsolutePath().replace(root, "");
    }

    private RouteHandler createHandler(final File file) {
        return new RouteHandler()
        {
            @Override
            public Response handle(IHTTPSession session)
            {
                return NanoHTTPD.newChunkedResponse(
                    Response.Status.OK,
                    "text/html",
                    getInputStream(file)
                );
            }
        };
    }

    private static FileInputStream getInputStream(File file) {
        try
        {
            return new FileInputStream(file);
        }
        catch (FileNotFoundException e)
        {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public static Response sendFile(String URI) {
        return NanoHTTPD.newChunkedResponse(
            Response.Status.OK,
            MimeTypeMap.getSingleton().getMimeTypeFromExtension(URI),
            getInputStream(new File(filePaths.get(URI)))
        );
    }

    public static String getBaseURI() {
        return baseURI;
    }
}

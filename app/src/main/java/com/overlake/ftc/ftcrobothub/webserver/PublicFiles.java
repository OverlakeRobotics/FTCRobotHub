package com.overlake.ftc.ftcrobothub.webserver;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.os.Environment;

import com.overlake.ftc.ftcrobothub.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.NanoHTTPD.Response;
import fi.iki.elonen.NanoHTTPD.IHTTPSession;
import fi.iki.elonen.NanoHTTPD.Method;

public class PublicFiles implements IRoute
{
    private String baseURI;
    private String filePath;
    private String root;

    public PublicFiles(String baseURI, Context context)
    {
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
        setPublicRoutes(filePath, routingTable);
    }

    private void setPublicRoutes(String path, Map<RoutingPath, RouteHandler> routingTable) {
        File file = getFile(path);
        if (file.isFile()) {
            addHandlerForFile(file, routingTable);
        } else {
            for (File child : file.listFiles()) {
                setPublicRoutes(path + "/" + child.getName(), routingTable);
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

    private InputStream getInputStream(File file) {
        try
        {
            return new FileInputStream(file);
        }
        catch (FileNotFoundException e)
        {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}

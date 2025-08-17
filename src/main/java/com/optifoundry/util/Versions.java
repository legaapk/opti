
package com.optifoundry.util;

import org.bukkit.Bukkit;

public final class Versions {
    public static boolean isAtLeast(int major, int minor, int patch) {
        String v = Bukkit.getBukkitVersion();
        try {
            String[] a = v.split("-")[0].split(".");
            int M = Integer.parseInt(a[0]);
            int m = a.length > 1 ? Integer.parseInt(a[1]) : 0;
            int p = a.length > 2 ? Integer.parseInt(a[2]) : 0;
            if (M != major) return M > major;
            if (m != minor) return m > minor;
            return p >= patch;
        } catch (Throwable ignored) { return false; }
    }
}

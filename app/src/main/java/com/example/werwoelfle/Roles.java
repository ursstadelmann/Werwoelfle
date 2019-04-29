package com.example.werwoelfle;

import android.content.Context;
import android.content.res.Resources;

public enum Roles {
    WEREWOLF,
    VILLAGER,
    CUPID,
    SEER,
    WITCH,
    HUNTER;

    public String getLabel(Context context) {
        Resources res = context.getResources();
        int resId = res.getIdentifier(this.name(), "string", context.getPackageName());
        if (0 != resId) {
            return (res.getString(resId));
        }
        return (name());
    }
}

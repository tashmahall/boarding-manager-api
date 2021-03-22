package com.igor.boardingmanager.controller.components;

import org.springframework.http.MediaType;

public final class PatchMediaType {
	public static final String APPLICATION_MERGE_PATCH_VALUE = "application/merge-patch+json";
    public static final MediaType APPLICATION_MERGE_PATCH;
    static {
        APPLICATION_MERGE_PATCH = MediaType.valueOf(APPLICATION_MERGE_PATCH_VALUE);
    }
    private PatchMediaType() {
        throw new IllegalStateException();
    }
}

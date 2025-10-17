package com.dauntlesstechnologies.ssk.maintenance_requests;

public record UpdateStatusDto(
        //we are sending the primary key via the link
        Status status
) {
}

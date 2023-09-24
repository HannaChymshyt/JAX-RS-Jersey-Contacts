package org.example.app.service;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.app.entity.Contact;

import java.net.URI;
import java.util.*;

@Path("/api/v1.0/contacts")
@Produces({MediaType.APPLICATION_JSON})
public class ContactService {

    private static final List<Contact> contacts;

    static {
        contacts = new ArrayList<>();
        contacts.add(new Contact(1L, "Luke", "1-587-998-2856"));
        contacts.add(new Contact(2L, "Leia", "1-403-249-2935"));
        contacts.add(new Contact(3L, "Padme", "1-587-122-3425"));
        contacts.add(new Contact(4L, "Anakin", "1-403-888-6352"));
    }

    @GET
    public List<Contact> getContacts() {
        return contacts;
    }

    @GET
    @Path("{id: [0-9]+}")
    public Contact getContact(@PathParam("id") Long id) {
        Contact contact = new Contact(id, null, null);

        int index = Collections.binarySearch(contacts, contact, Comparator.comparing(Contact::getId));

        if (index >= 0)
            return contacts.get(index);
        else
            throw new WebApplicationException(Response.Status.NOT_FOUND);
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createContact(Contact contact) {
        if (Objects.isNull(contact.getId()))
            throw new WebApplicationException(Response.Status.BAD_REQUEST);

        int index = Collections.binarySearch(contacts, contact, Comparator.comparing(Contact::getId));

        if (index < 0) {
            contacts.add(contact);
            return Response
                    .status(Response.Status.CREATED)
                    .location(URI.create(String.format("/api/v1.0/contacts/%s", contact.getId())))
                    .build();
        } else
            throw new WebApplicationException(Response.Status.CONFLICT);
    }


    @PUT
    @Path("{id: [0-9]+}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response updateContact(@PathParam("id") Long id, Contact contact) {
        contact.setId(id);
        int index = Collections.binarySearch(contacts, contact, Comparator.comparing(Contact::getId));

        if (index >= 0) {
            Contact updatedContact = contacts.get(index);
            updatedContact.setPhone(contact.getPhone());
            contacts.set(index, updatedContact);
            return Response
                    .status(Response.Status.NO_CONTENT)
                    .build();
        } else
            throw new WebApplicationException(Response.Status.NOT_FOUND);
    }

    @DELETE
    @Path("{id: [0-9]+}")
    public Response deleteContact(@PathParam("id") Long id) {
        Contact contact = new Contact(id, null, null);
        int index = Collections.binarySearch(contacts, contact, Comparator.comparing(Contact::getId));

        if (index >= 0) {
            contacts.remove(index);
            return Response
                    .status(Response.Status.NO_CONTENT)
                    .build();
        } else
            throw new WebApplicationException(Response.Status.NOT_FOUND);
    }

}

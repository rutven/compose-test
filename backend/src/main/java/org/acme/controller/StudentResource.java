package org.acme.controller;

import java.net.URI;
import java.util.List;

import org.acme.model.Student;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/students")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StudentResource {

    @GET
    public List<Student> getAll() {
        return Student.listAll();
    }

    @Path("/{id}")
    @GET
    public Student get(Long id) {
        return Student.findById(id);
    }

    @POST
    @Transactional
    public Response create(Student student) {
        student.persist();
        return Response.created(URI.create("/students/" + student.id)).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Student update(Long id, Student student) {
        Student entity = Student.findById(id);

        if (entity == null) {
            throw new NotFoundException();
        }
        entity.firstName = student.firstName;
        entity.lastName = student.lastName;
        entity.email = student.email;

        return entity;
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void delete(Long id) {
        Student student = Student.findById(id);

        if (student == null) {
            throw new NotFoundException();
        }

        student.delete();
    }

    @GET
    @Path("/count")
    public Long count() {
        return Student.count();
    }
}

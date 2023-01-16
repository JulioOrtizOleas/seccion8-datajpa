package com.cursoudemy.springboot.app.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "clientes")
public class Cliente implements Serializable {

    @Id
    // indicar que es autoincremental
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //@NotEmpty son validaciones para los campos , es decir que que no tengas valores cero
    @NotEmpty
    private String nombre;
    @NotEmpty
    private String apellido;
    @NotEmpty
    @Email
    private String email;
    // @Column customisar o indicar el nombre de la columna de la bade de datos
    @NotNull
    @Column(name = "create_at")
    // @Temporal Indicamos el formato que se va a guardar la fecha en la base de datos
    @Temporal(TemporalType.DATE)
    // para darle formato a la fecha
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createAt;
    // proceso de convertir un objeto en una secuencia de bytes para transmitirlo a una BBDD o JSON o memoria..
    private static final long serialVersionUID = 1L;

    // Añadimos la fecha de forma automatica.
    //@PrePersist
    //public void prePersist(){
    //    createAt=new Date();
   // }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
}
// para personalizar los mensajes de error podemos crear un file "messages properties", en resources
// typeMismatch sirve para hacer mas amigable el mensaje de error que devuelve el sistema con la fecha.
// Otra forma de controlar los errores es en el docu HTM form creando el siguiente codigo:
/*<div th:object="${cliente}" th:remove="tag">
<ul th:if="${#fields.hasErrors('*')}" class="alert alert-danger">
<li th:each="err: ${#fields.errors('*')}" th:text="${err}"></li>
</ul>
</div>
 */
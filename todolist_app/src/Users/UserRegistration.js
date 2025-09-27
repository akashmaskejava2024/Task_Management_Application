import { ErrorMessage, Field, Form, Formik } from "formik";
import * as Yup from "yup";
import './UserRegistration.css';
import { AddUser } from "../ActionCreator/UserActionCreator";
import { useNavigate } from "react-router-dom";
import { useDispatch } from "react-redux";

export const UserRegistration = () => {
    const navigate = useNavigate();
    const dispatch = useDispatch();
    const validationSchema = Yup.object().shape({
        name: Yup.string().required("Name is required"),
        dob: Yup.date().required("Date of Birth is required").nullable(),
        email: Yup.string().email("Invalid email").required("Email is required"),
        phone: Yup.string()
            .matches(/^[0-9]{10}$/, "Phone number must be 10 digits")
            .required("Phone number is required"),
            username: Yup.string()
            .trim()
            .required("Mandatory")
            .min(4, "Minimum 4 characters")
            .max(10, "Max 10 characters")
            .matches(/^[a-zA-Z]+$/, "Name can only contain letters"),
        password: Yup.string()
            .trim()
            .required("Mandatory")
            .min(8, "Minimum 8 characters") // Fixed minimum character count
            .matches(/[A-Z]/, "Password must contain at least one uppercase letter")
            .matches(/[a-z]/, "Password must contain at least one lowercase letter")
            .matches(/[0-9]/, "Password must contain at least one number")
            .matches(/[\W_]/, "Password must contain at least one special character")
    
    });

    return (
        <Formik
            initialValues={{
                name: '',
                dob: '',
                email: '',
                phone: '',
                username: '',
                password: ''
            }}
            validationSchema={validationSchema}
            onSubmit={(user) => {
              
                AddUser(navigate,dispatch ,user);
            }}
        >
            {({ errors, touched }) => (
                <div className="container mt-5">
                    <h2>User Registration</h2>
                    <Form>
                        <div className="form-group">
                            <label htmlFor="name">Name</label>
                            <Field type="text" id="name" name="name" className={`form-control ${errors.name && touched.name ? 'is-invalid' : ''}`} />
                            <ErrorMessage className="ErrorMsg" component="span" name="name"/>
                        </div>
                        <div className="form-group">
                            <label htmlFor="dob">Date of Birth</label>
                            <Field type="date" id="dob" name="dob" className={`form-control ${errors.dob && touched.dob ? 'is-invalid' : ''}`} />
                            <ErrorMessage className="ErrorMsg"  component="span" name="dob"/>

                        </div>
                        <div className="form-group">
                            <label htmlFor="email">Email</label>
                            <Field type="email" id="email" name="email" className={`form-control ${errors.email && touched.email ? 'is-invalid' : ''}`} />
                            <ErrorMessage className="ErrorMsg"  component="span" name="email"/>
                            </div>
                        <div className="form-group">
                            <label htmlFor="phone">Phone</label>
                            <Field type="tel" id="phone" name="phone" className={`form-control ${errors.phone && touched.phone ? 'is-invalid' : ''}`} />
                            <ErrorMessage className="ErrorMsg"  component="span" name="phone"/>
                        </div>
                        <div className="form-group">
                            <label htmlFor="username">Username</label>
                            <Field type="text" id="username" name="username" className={`form-control ${errors.username && touched.username ? 'is-invalid' : ''}`} />
                            <ErrorMessage className="ErrorMsg"  component="span" name="username"/>
                            </div>
                        <div className="form-group">
                            <label htmlFor="password">Password</label>
                            <Field type="password" id="password" name="password" className={`form-control ${errors.password && touched.password ? 'is-invalid' : ''}`} />
                            <ErrorMessage className="ErrorMsg"  component="span" name="password"/>

                        </div>
                        <div  className="d-flex justify-content-end w-100    ">
                        <button type="submit" className="btn btn-primary mt-5">Submit</button>
                        </div>
                        
                    </Form>
                </div>
            )}
        </Formik>
    );
}

export default UserRegistration;

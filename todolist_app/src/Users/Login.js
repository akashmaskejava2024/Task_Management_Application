import { ErrorMessage, Field, Form, Formik } from 'formik';
import './Login.css';
import * as Yup from 'yup';
import { Link, useNavigate } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { validateLogin } from '../ActionCreator/UserActionCreator';

export const Login = () => {
    const users = useSelector((state) => state.users);
    const navigate = useNavigate();
    const dispatch = useDispatch();

    return (
        <Formik
            initialValues={{
                username: '',
                password: ''
            }}
            validationSchema={Yup.object().shape({
                username: Yup.string()
                    .trim()
                    .required("Mandatory")
                    .min(4, "Minimum 4 characters")
                    .max(10, "Max 10 characters")
                    .matches(/^[a-zA-Z]+$/, "Name can only contain letters"),
                password: Yup.string()
                    .trim()
                    .required("Mandatory")
                    .min(5, "Minimum 5 characters")
                    .matches(/[A-Z]/, "Password must contain at least one uppercase letter")
                    .matches(/[a-z]/, "Password must contain at least one lowercase letter")
                    .matches(/[0-9]/, "Password must contain at least one number")
                    .matches(/[\W_]/, "Password must contain at least one special character")
            })}
            onSubmit={(loginData) => {
                validateLogin(navigate, loginData, dispatch);
            }}
        >
            {({ errors, touched }) => (
                <Form>
                    <div className="LoginCard m-auto p-5">
                        <div className="row g-3 align-items-center">
                            <div className="col-auto">
                                <label htmlFor="username" className="col-form-label">Username</label>
                            </div>
                            <div className="col-auto">
                                <Field
                                    type="text"
                                    id="username"
                                    name="username"
                                    className={`form-control ${touched.username && errors.username ? 'is-invalid' : ''}`}
                                />
                                <ErrorMessage name='username' component="span" className="form-text text-danger" />
                            </div>
                            <div className="col-auto">
                                <label htmlFor="inputPassword6" className="col-form-label">Password</label>
                            </div>
                            <div className="col-auto">
                                <Field
                                    type="password"
                                    id="inputPassword6"
                                    name="password"
                                    className={`form-control ${touched.password && errors.password ? 'is-invalid' : ''}`}
                                />
                                <ErrorMessage name='password' component="span" className="form-text text-danger" />
                            </div>
                            
                            <div className="col-auto d-flex justify-content-between w-100">
                                <Link to='/Register' className="btn btn-link">Sign up</Link>
                                <Link to='/ResendToken' className="btn btn-link">Resend Email V.</Link>

                                <button type='submit' className='btn btn-success'>Login</button>
                            </div>
                        </div>
                    </div>
                </Form>
            )}
        </Formik>
    );
};

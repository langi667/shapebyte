import shared

extension UIState {
    func viewData<T: AnyObject>() -> T? {
        return (self as? UIStateData<T>)?.data
    }
}

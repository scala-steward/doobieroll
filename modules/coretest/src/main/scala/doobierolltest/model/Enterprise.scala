package doobierolltest.model

import java.util.UUID

final case class Enterprise(
  id: UUID,
  name: String,
  departments: Vector[Department],
  invoices: Vector[Invoice],
)

object Enterprise {
  def fromDb(
    db: DbCompany,
    departments: Vector[Department],
    invoices: Vector[Invoice],
  ): Enterprise =
    Enterprise(
      id = db.id,
      name = db.name,
      departments = departments,
      invoices = invoices,
    )

  def fromDbFallible(
    db: DbCompany,
    departments: Vector[Department],
    invoices: Vector[Invoice],
  ): Either[Err, Enterprise] = {
    Either.cond(
      !db.name.contains("err"),
      Enterprise(
        id = db.id,
        name = db.name,
        departments = departments,
        invoices = invoices,
      ),
      Err(s"enterprise ${db.name}"),
    )
  }
}
